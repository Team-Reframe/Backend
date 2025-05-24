package backend.receipt.point.controller;

import backend.receipt.point.DTO.PointRewardResponse;
import backend.receipt.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    // ✅ 포인트 지급
    @PostMapping("/reward")
    public ResponseEntity<PointRewardResponse> rewardPoint(
            Principal principal,
            @RequestParam Long purchaseId
    ) {
        Long memberId = Long.parseLong(principal.getName()); // JWT 토큰에서 추출된 ID
        PointRewardResponse response = pointService.givePoint(memberId, purchaseId);
        return ResponseEntity.ok(response);
    }

    /*// ✅ 포인트 총합 조회
    @GetMapping("/total")
    public ResponseEntity<PointRewardResponse> getTotalPoints(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        PointRewardResponse response = pointService.getTotalPointResponse(memberId);
        return ResponseEntity.ok(response);
    }*/

    // ✅ 예외 핸들링 (선택)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //현재 포인트 조회
    @GetMapping("/total")
    public ResponseEntity<PointRewardResponse> getTotalPointTest(@RequestParam Long memberId) {
        PointRewardResponse response = pointService.getTotalPointResponse(memberId);
        return ResponseEntity.ok(response);
    }

}
