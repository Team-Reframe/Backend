package backend.receipt.review.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.purchase.domain.Purchase;
import backend.receipt.purchase.repository.PurchaseRepository;
import backend.receipt.review.domain.Review;
import backend.receipt.review.dto.request.ReviewRequest;
import backend.receipt.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository;

    public void createReview(ReviewRequest reviewRequest) {
        Member member = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Purchase purchase = purchaseRepository.findById(reviewRequest.getPurchaseId())
                .orElseThrow(() -> new IllegalArgumentException("구매 정보를 찾을 수 없습니다."));

        Review review = Review.builder()
                .member(member)
                .purchase(purchase)
                .content(reviewRequest.getContent())
                .rating(reviewRequest.getRating())
                .build();

        reviewRepository.save(review);
    }
}
