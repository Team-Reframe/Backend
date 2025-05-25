package backend.receipt.store.controller;

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
    public ResponseEntity<List<StoreResponse>> getStoresList() {
        List<StoreResponse> stores = storeService.getStoresList();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/map")
    public List<StoreResponse> getStoresInMap(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return storeService.findStoresInArea(swLat, swLng, neLat, neLng);
    }
}