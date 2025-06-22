package backend.receipt.store.service;

import backend.receipt.store.domain.Store;
import backend.receipt.store.dto.request.StoreRequest;
import backend.receipt.store.dto.response.KakaoPlaceDetail;
import backend.receipt.store.dto.response.StoreResponse;
import backend.receipt.store.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Value("${spring.kakao.api.key.rest}")
    private String kakaoApiKeyRest;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // DB 가맹점 전체조회
    public List<StoreResponse> getStoresList() {
        return storeRepository.findAll().stream()
                .map(StoreResponse::new)
                .collect(Collectors.toList());
    }

    //지도상 bound 값에 따른 가맹점 조회
    public List<StoreResponse> findStoresInArea(double swLat, double swLng, double neLat, double neLng) {
        double centerLat = (swLat + neLat) / 2;
        double centerLng = (swLng + neLng) / 2;
        int radius = (int) ((Math.abs(neLat - swLat) * 111000) / 2);

        List<StoreResponse> kakaoResults = searchStoresFromKakao("카페", centerLng, centerLat, radius);
        return filterApprovedStores(kakaoResults);
    }
    // 설정한 가맹점로 필터링
    private List<StoreResponse> filterApprovedStores(List<StoreResponse> kakaoResults) {
        Set<String> approvedNames = storeRepository.findAll().stream()
                .map(Store::getName)
                .collect(Collectors.toSet());

        return kakaoResults.stream()
                .filter(kakao -> approvedNames.contains(kakao.getName()))
                .collect(Collectors.toList());
    }

    // 이름으로 검색후 DB 저장
    public StoreResponse searchAndSaveStoreByName(StoreRequest request) {
        // 좌표와 반경 기본값 설정(시흥시 전역)
        double x = request.getX() != null ? request.getX() : 126.81;
        double y = request.getY() != null ? request.getY() : 37.38;
        int radius = request.getRadius() != null ? request.getRadius() : 7000;

        List<StoreResponse> kakaoResults = searchStoresFromKakao(
                request.getQuery(), x, y, radius
        );

        Optional<StoreResponse> matched = kakaoResults.stream()
                .filter(store -> store.getName().equals(request.getName()))
                .findFirst();

        if (matched.isPresent()) {
            StoreResponse response = matched.get();

            boolean exists = storeRepository.existsByName(response.getName());
            if (!exists) {
                Store store = Store.of(
                        response.getName(),
                        response.getCategory(),
                        response.getAddress(),
                        response.getLatitude(),
                        response.getLongitude()
                );
                storeRepository.save(store);
            }

            return response;
        } else {
            throw new RuntimeException("카카오 API에서 해당 이름의 가맹점을 찾을 수 없습니다.");
        }
    }

    //가맹점 상세 조회
    public StoreResponse getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("id가 " + storeId + "인 가맹점을 찾을 수 없습니다."));

        StoreResponse response = new StoreResponse(store);

        KakaoPlaceDetail kakaoDetail = fetchKakaoPlaceDetail(store.getName());

        if (kakaoDetail != null) {
            if (kakaoDetail.getDescription() != null) response.setDescription(kakaoDetail.getDescription());
            if (kakaoDetail.getPhone() != null) response.setPhone(kakaoDetail.getPhone());
            if (kakaoDetail.getOpeningHours() != null) response.setOpeningHours(kakaoDetail.getOpeningHours());
        }

        return response;
    }

    //초기데이터
    @PostConstruct
    public void initStoresOnStartup() {
        try {
            StoreRequest store1 = new StoreRequest();
            store1.setQuery("베이커리");
            store1.setName("파리바게뜨 시흥장현점");
            searchAndSaveStoreByName(store1);

            StoreRequest store2 = new StoreRequest();
            store2.setQuery("카페");
            store2.setName("일리카페 시흥시청점");
            searchAndSaveStoreByName(store2);

            StoreRequest store3 = new StoreRequest();
            store3.setQuery("편의점");
            store3.setName("CU 시흥시청점");
            searchAndSaveStoreByName(store3);

            System.out.println("[초기 가맹점 등록 완료]");
        } catch (Exception e) {
            System.err.println("[초기 가맹점 등록 실패] " + e.getMessage());
        }
    }
    // kakao api 관련
    // kakao api 호출
    private String fetchKakaoApiResponse(String query, double x, double y, int radius) {
        String encodedKeyword = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String apiUrl = String.format(
                "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s&x=%f&y=%f&radius=%d",
                encodedKeyword, x, y, radius
        );

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + kakaoApiKeyRest);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return br.lines().collect(Collectors.joining());
        } catch (Exception e) {
            throw new RuntimeException("Kakao API 요청 실패: " + e.getMessage());
        }
    }

    // 응답 JSON 파싱
    private List<StoreResponse> searchStoresFromKakao(String query, double x, double y, int radius) {
        String response = fetchKakaoApiResponse(query, x, y, radius);
        return parseKakaoResponse(response);
    }
    private List<StoreResponse> parseKakaoResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONArray documents = json.getJSONArray("documents");
            List<StoreResponse> results = new ArrayList<>();

            for (int i = 0; i < documents.length(); i++) {
                JSONObject obj = documents.getJSONObject(i);
                Store store = Store.of(
                        obj.getString("place_name"),
                        obj.optString("category_name", "기타"),
                        obj.getString("road_address_name"),
                        Double.parseDouble(obj.getString("y")),
                        Double.parseDouble(obj.getString("x"))
                );
                results.add(new StoreResponse(store));
            }

            return results;
        } catch (Exception e) {
            throw new RuntimeException("Kakao 응답 파싱 실패: " + e.getMessage());
        }
    }

    // 카카오 상제조회 호출
    private KakaoPlaceDetail fetchKakaoPlaceDetail(String placeName) {
        try {
            String encodedName = URLEncoder.encode(placeName, StandardCharsets.UTF_8);
            String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + encodedName;

            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + kakaoApiKeyRest);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String responseStr = br.lines().collect(Collectors.joining());

            JSONObject json = new JSONObject(responseStr);
            JSONArray documents = json.getJSONArray("documents");

            if (documents.length() > 0) {
                JSONObject place = documents.getJSONObject(0);
                KakaoPlaceDetail detail = new KakaoPlaceDetail();

                // 카카오 API 상세 정보 필드 (필요시 조정)
                detail.setDescription(place.optString("place_name", null));
                detail.setPhone(place.optString("phone", null));
                detail.setOpeningHours(place.optString("opening_hours", null));

                return detail;
            }
        } catch (Exception e) {
            throw new RuntimeException("카카오 Place 상세정보 조회 실패: " + e.getMessage());
        }
        return null;
    }
}