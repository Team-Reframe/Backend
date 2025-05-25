package backend.receipt.point.controller;

import backend.receipt.member.repository.MemberRepository;
import backend.receipt.point.dto.PointRequest;
import backend.receipt.point.dto.PointResponse;
import backend.receipt.point.repository.PointRepository;
import backend.receipt.point.service.PointService;
import backend.receipt.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;


    @PostMapping("/reward")
    public ResponseEntity<PointResponse> givePoint(@RequestBody PointRequest request) {
        PointResponse response = pointService.givePoint(
                Long.valueOf(request.getMemberId()),
                Long.valueOf(request.getPurchaseId()),
                Integer.valueOf(request.getAmount())
        );
        return ResponseEntity.ok(response);
    }
}

