package backend.receipt.love.service;

import backend.receipt.love.domain.Love;
import backend.receipt.love.dto.LoveResponse;
import backend.receipt.love.repository.LoveRepository;
import backend.receipt.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoveTotalService {
    private final LoveRepository loveRepository;
    public List<LoveResponse> getLovedStores(Long memberId) {

        List<Love> loves = loveRepository.getTotalLoves(memberId);

        return loves.stream()
                .map(love -> {
                    Store store = love.getStore();
                    return new LoveResponse(
                            store.getId(),
                            store.getName(),
                            store.getAddress()
                    );
                })
                .collect(Collectors.toList());
    }
}
