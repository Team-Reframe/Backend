package backend.receipt.purchase.controller;

import backend.receipt.purchase.dto.PurchaseDto;
import backend.receipt.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Purchase", description = "영수증 등록 API")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "영수증 업로드", description = "이미지 파일을 업로드하면 OCR로 인식된 가맹점명, 사업자번호, 결제금액, 결제일자를 반환합니다.")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("memberId") Long memberId
    ) {
        try {
            PurchaseDto response = purchaseService.handleReceipt(file, memberId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("유효하지 않은 요청: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("영수증 OCR 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다.");
        }
    }
}

