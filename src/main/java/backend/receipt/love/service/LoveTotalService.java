package backend.receipt.love.service;

import backend.receipt.love.repository.LoveRepository;
import backend.receipt.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoveTotalService {

    private final LoveRepository loveRepository;
    public List<Store> getTotalLove(Long memberId){
        return loveRepository.getTotalLoves(memberId);
    }
}
