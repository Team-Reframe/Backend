package backend.receipt.point.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.point.domain.Point;
import backend.receipt.point.domain.PointType;
import backend.receipt.point.DTO.PointRewardResponse;
import backend.receipt.point.repository.PointRepository;
import backend.receipt.purchase.domain.Purchase;
import backend.receipt.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    public PointRewardResponse givePoint(Long memberId, Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영수증이 존재하지 않습니다."));

        int rand = (int) (Math.random() * 1000) + 1;
        double rate;

        if (rand <= 500) {
            rate = 0.005;
        } else if (rand <= 700) {
            rate = 0.01;
        } else if (rand <= 900) {
            rate = 0.03;
        } else if (rand <= 970) {
            rate = 0.05;
        } else {
            rate = 0.10;
        }

        int pointAmount = (int) Math.round(purchase.getAmount() * rate);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Point point = Point.builder()
                .member(member)
                .purchase(purchase)
                .amount(pointAmount)
                .type(PointType.REWARD)
                .createdAt(LocalDateTime.now())
                .build();

        pointRepository.save(point);

        int total = pointRepository.getTotalPoints(memberId, PointType.REWARD);
        return new PointRewardResponse(pointAmount, total);
    }

    public PointRewardResponse getTotalPointResponse(Long memberId) {
        Integer total = pointRepository.getTotalPoints(memberId, PointType.REWARD);
        return new PointRewardResponse(0, total != null ? total : 0);
    }

    public int getTotalPoint(Long memberId) {
        Integer total = pointRepository.getTotalPoints(memberId, PointType.REWARD);
        return total != null ? total : 0;
    }


}


