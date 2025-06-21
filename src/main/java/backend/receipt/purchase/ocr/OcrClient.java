package backend.receipt.purchase.ocr;

import backend.receipt.global.config.NaverOcrProperties;
import backend.receipt.purchase.dto.PurchaseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OcrClient {

    private final NaverOcrProperties naverOcrProperties;

    public PurchaseDto extractInfo(MultipartFile file) throws Exception {
        String boundary = UUID.randomUUID().toString();
        String LINE_FEED = "\r\n";
        String charset = "UTF-8";

        String message = String.format("{\"version\":\"V2\",\"requestId\":\"%s\",\"timestamp\":%d,\"lang\":\"ko\",\"images\":[{\"format\":\"jpg\",\"name\":\"%s\"}]}",
                UUID.randomUUID(), System.currentTimeMillis(), file.getOriginalFilename());

        URL url = new URL(naverOcrProperties.getInvokeUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("X-OCR-SECRET", naverOcrProperties.getSecretKey());

        try (DataOutputStream request = new DataOutputStream(conn.getOutputStream())) {
            // 파일 파트
            request.writeBytes("--" + boundary + LINE_FEED);
            request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getOriginalFilename() + "\"" + LINE_FEED);
            request.writeBytes("Content-Type: " + file.getContentType() + LINE_FEED);
            request.writeBytes(LINE_FEED);
            request.write(file.getBytes());
            request.writeBytes(LINE_FEED);

            // message 파트
            request.writeBytes("--" + boundary + LINE_FEED);
            request.writeBytes("Content-Disposition: form-data; name=\"message\"" + LINE_FEED);
            request.writeBytes("Content-Type: application/json; charset=" + charset + LINE_FEED);
            request.writeBytes(LINE_FEED);
            request.writeBytes(message + LINE_FEED);
            request.writeBytes("--" + boundary + "--" + LINE_FEED);
            request.flush();
        }

        // 응답 파싱
        String response = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode fields = root.at("/images/0/fields");

        String storeName = "";
        int amount = 0;
        String paymentDate = null;

        // 1. 가맹점명: 가장 위의 inferText 값 사용
        if (fields.isArray() && fields.size() > 0) {
            storeName = fields.get(0).get("inferText").asText();
        }

        // 2. 총 금액: "합계", "총액", "금액", "원" 등의 키워드 인근 숫자 추출
        for (int i = 0; i < fields.size(); i++) {
            String text = fields.get(i).get("inferText").asText();
            if (text.contains("합계") || text.contains("총액") || text.contains("금액") || text.contains("₩") || text.contains("원")) {
                if (i + 1 < fields.size()) {
                    String nextText = fields.get(i + 1).get("inferText").asText().replaceAll("[^0-9]", "");
                    if (!nextText.isEmpty()) {
                        amount = Integer.parseInt(nextText);
                        break;
                    }
                }
            }
        }

        // 3. 결제일자: yyyy-MM-dd 또는 yyyy.MM.dd 또는 yyyy/MM/dd 형태 추출
        for (JsonNode field : fields) {
            String text = field.get("inferText").asText();
            if (text.matches("20[0-9]{2}[-./][0-9]{1,2}[-./][0-9]{1,2}")) {
                paymentDate = text.replaceAll("[./]", "-");

                String[] parts = paymentDate.split("-");
                if (parts.length == 3) {
                    String yyyy = parts[0];
                    String mm = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
                    String dd = parts[2].length() == 1 ? "0" + parts[2] : parts[2];
                    paymentDate = yyyy + "-" + mm + "-" + dd;
                }
                break;
            }
        }

        return PurchaseDto.builder()
                .storeName(storeName)
                .amount(amount)
                .paymentDate(paymentDate)
                .build();
    }
}
