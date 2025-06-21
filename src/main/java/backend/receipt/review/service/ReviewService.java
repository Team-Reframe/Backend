package backend.receipt.review.service;

import backend.receipt.member.domain.Member;
import backend.receipt.member.repository.MemberRepository;
import backend.receipt.purchase.domain.Purchase;
import backend.receipt.purchase.repository.PurchaseRepository;
import backend.receipt.review.domain.Review;
import backend.receipt.review.dto.request.ReviewRequest;
import backend.receipt.review.dto.response.ReviewResponse;
import backend.receipt.review.repository.ReviewRepository;
import backend.receipt.store.domain.Store;
import backend.receipt.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository;
    private final StoreRepository storeRepository;

    public void createReview(ReviewRequest reviewRequest) {
        Member member = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Purchase purchase = purchaseRepository.findById(reviewRequest.getPurchaseId())
                .orElseThrow(() -> new IllegalArgumentException("구매 정보를 찾을 수 없습니다."));

        Store store = storeRepository.findById(reviewRequest.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("가맹점을 찾을 수 없습니다."));

        String createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));

        Review review = Review.builder()
                .member(member)
                .purchase(purchase)
                .store(store)
                .content(reviewRequest.getContent())
                .rating(reviewRequest.getRating())
                .createdAt(createdAt)
                .build();

        reviewRepository.save(review);
    }


    public List<ReviewResponse> getReviewsByStoreId(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);

        return reviews.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        return ReviewResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public List<ReviewResponse> getReviewsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return reviewRepository.findByMember(member).stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getId())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
