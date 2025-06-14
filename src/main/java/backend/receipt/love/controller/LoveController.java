package backend.receipt.love.controller;

import backend.receipt.love.service.LoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoveController {
    private final LoveService loveService;

    @PostMapping("/love/add")
    public ResponseEntity<String> addLove(@RequestParam Long memberId, @RequestParam Long storeId) {
        boolean added = loveService.loveService(memberId, storeId);
        if (added) {
            return ResponseEntity.ok("즐겨찾기 등록 완료");
        } else {
            return ResponseEntity.ok("즐겨찾기 등록 완료");

        }
    }
}
