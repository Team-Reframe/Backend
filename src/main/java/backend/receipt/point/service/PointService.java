package backend.receipt.point.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.point.domain.Point;
import backend.receipt.point.domain.PointType;
import backend.receipt.point.dto.response.PointResponse;
import backend.receipt.point.repository.PointRepository;
import backend.receipt.purchase.domain.Purchase;
import backend.receipt.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;

    public PointResponse givePoint(Long memberId, Long purchaseId, Integer amount) {
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

        int points = (int) Math.round(amount * rate);


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Point point = Point.builder()
                .member(member)
                .purchase(purchase)
                .points(points)
                .type(PointType.POINT)
                .build();

        pointRepository.save(point);

        return new PointResponse(points, purchaseId, memberId, PointType.POINT, LocalDateTime.now());
    }



}


