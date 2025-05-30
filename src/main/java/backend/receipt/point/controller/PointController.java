package backend.receipt.point.controller;


import backend.receipt.point.dto.request.PointRequest;
import backend.receipt.point.dto.request.PointTotalRequest;
import backend.receipt.point.dto.response.PointHistoryResponse;
import backend.receipt.point.dto.response.PointResponse;
import backend.receipt.point.dto.response.PointTotalResponse;
import backend.receipt.point.service.PointHistoryService;
import backend.receipt.point.service.PointService;
import backend.receipt.point.service.PointTotalService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Point", description = "포인트 API")
public class PointController {

    private final PointService pointService;
    private final PointTotalService pointTotalService;
    private final PointHistoryService pointHistoryService;

    @PostMapping("/reward")
    public ResponseEntity<PointResponse> givePoint(@RequestBody PointRequest request) {
        PointResponse response = pointService.givePoint(
                request.getMemberId(),
                request.getPurchaseId(),
                request.getAmount()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")

    public PointTotalResponse getTotalPoints(@RequestParam("memberId") Long memberId) {
        return pointTotalService.getTotalPoints(new PointTotalRequest(memberId));
    }


    @GetMapping("/{memberId}/history")
    public ResponseEntity<List<PointHistoryResponse>> getPointHistory(@PathVariable Long memberId) {
        List<PointHistoryResponse> history = pointHistoryService.getHistory(memberId);
        return ResponseEntity.ok(history);
    }

}

