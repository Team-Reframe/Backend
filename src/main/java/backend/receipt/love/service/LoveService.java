package backend.receipt.love.service;

import backend.receipt.love.domain.Love;
import backend.receipt.love.repository.LoveRepository;
import backend.receipt.store.domain.Store;
import backend.receipt.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LoveService {
    private final LoveRepository loveRepository;
    private final StoreRepository storeRepository;

    public boolean loveService(Long memberId, Long storeId) {

        Optional<Love> existingLove = loveRepository.findByMemberIdAndStoreId(memberId, storeId);
        if (existingLove.isPresent()) {
            loveRepository.delete(existingLove.get()); //즐겨찾기 취소
            return false;
        }
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("Store not found"));
        Love love = new Love();
        love.initialize(memberId, store);
        loveRepository.save(love);
        return true;
    }
}
