package backend.receipt.love.controller;

import backend.receipt.love.dto.LoveResponse;
import backend.receipt.love.service.LoveService;
import backend.receipt.love.service.LoveTotalService;
import backend.receipt.store.domain.Store;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "Love", description = "즐겨찾기 API")
public class LoveController {
    private final LoveService loveService;
    private final LoveTotalService loveTotalService;

    @PostMapping("/love/add")
    public ResponseEntity<String> addLove(@RequestParam Long memberId, @RequestParam Long storeId) {
        boolean added = loveService.loveService(memberId, storeId);
        if (added) {
            return ResponseEntity.ok("즐겨찾기 등록 완료");
        } else {
            return ResponseEntity.ok("즐겨찾기 해제 완료");

        }
    }

    @PostMapping("/love/total")
    public ResponseEntity<List<LoveResponse>> getLovedStores(@RequestParam Long memberId) {
        List<LoveResponse> lovedStores = loveTotalService.getLovedStores(memberId);
        return ResponseEntity.ok(lovedStores);
    }
}
