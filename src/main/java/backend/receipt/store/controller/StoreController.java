package backend.receipt.store.controller;

import backend.receipt.store.dto.request.StoreRequest;
import backend.receipt.store.dto.response.StoreResponse;
import backend.receipt.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Store", description = "가맹점 API")
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(summary = "전체 가맹점 조회", description = "DB에 등록된 모든 가맹점 정보를 조회합니다.")
    @GetMapping
    public List<StoreResponse> getStoresList() {
        return storeService.getStoresList();
    }

    @Operation(summary = "지도 범위 내 가맹점 조회", description = "지도에서 지정한 경계 내에 있는 가맹점 정보를 조회합니다.")
    @Parameters({
            @Parameter(name = "swLat", description = "서남쪽 위도"),
            @Parameter(name = "swLng", description = "서남쪽 경도"),
            @Parameter(name = "neLat", description = "북동쪽 위도"),
            @Parameter(name = "neLng", description = "북동쪽 경도")
    })
    @GetMapping("/map")
    public List<StoreResponse> getStoresInMap(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng)
    {
        return storeService.findStoresInArea(swLat, swLng, neLat, neLng);
    }

    @Operation(summary = "가맹점 상세 조회", description = "가맹점 ID로 상세 정보를 조회합니다.")
    @Parameter(name = "storeId", description = "조회할 가맹점 ID")
    @GetMapping("/{storeId:\\d+}")
    public StoreResponse getStoreDetail(@PathVariable Long storeId) {
        return storeService.getStoreDetail(storeId);
    }

    //  Kakao API로부터 가맹점 검색 후 DB 저장
    @Operation(summary = "가맹점 데이터 추가", description = "카카오 API로부터 가맹점을 검색하여 DB에 저장합니다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "가맹점 검색 요청 정보",
            required = true,
            content = @Content(schema = @Schema(implementation = StoreRequest.class))
    )
    @PostMapping("/import")
    public StoreResponse importStoreByName(@RequestBody StoreRequest request) {
        return storeService.searchAndSaveStoreByName(request);
    }


}