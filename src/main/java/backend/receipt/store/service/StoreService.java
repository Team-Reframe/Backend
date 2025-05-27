package backend.receipt.store.service;

import backend.receipt.store.domain.Store;
import backend.receipt.store.dto.response.StoreResponse;
import backend.receipt.store.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService{
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreResponse> getStoresList() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(StoreResponse::new).collect(Collectors.toList());
    }

    public List<StoreResponse> findStoresInArea(double swLat, double swLng, double neLat, double neLng) {
        List<Store> stores = storeRepository.findByLocationInBounds(swLat, neLat, swLng, neLng);
        return stores.stream()
                .map(StoreResponse::new)
                .collect(Collectors.toList());
    }

}
