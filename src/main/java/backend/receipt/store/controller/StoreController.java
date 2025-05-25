package backend.receipt.store.controller;

import backend.receipt.store.dto.request.StoreRequest;
import backend.receipt.store.dto.response.StoreResponse;
import backend.receipt.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreResponse> getStoresList() {
        return storeService.getStoresList();
    }

    @GetMapping("/map")
    public List<StoreResponse> getStoresInMap(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng)
    {
        return storeService.findStoresInArea(swLat, swLng, neLat, neLng);
    }

    //  Kakao API로부터 가맹점 검색 후 DB 저장
    @PostMapping("/import")
    public StoreResponse importStoreByName(@RequestBody StoreRequest request) {
        return storeService.searchAndSaveStoreByName(request);
    }


}