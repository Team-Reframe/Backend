package backend.receipt.review.controller;

import backend.receipt.review.dto.request.ReviewRequest;
import backend.receipt.review.dto.response.ReviewResponse;
import backend.receipt.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰 내용을 작성하여 특정 가맹점에 리뷰를 등록합니다.")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok("리뷰 등록 완료");
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "가맹점 리뷰 전체 조회", description = "storeId를 기준으로 해당 가맹점의 리뷰를 모두 조회합니다.")
    public ResponseEntity<List<ReviewResponse>> getReviewsByStoreId(@PathVariable Long storeId) {
        List<ReviewResponse> responseList = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "리뷰 ID로 단일 리뷰 상세 내용을 조회합니다.")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        ReviewResponse response = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @Operation(summary = "내가 쓴 리뷰 조회", description = "로그인한 사용자가 작성한 모든 리뷰를 조회합니다.")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(@RequestParam Long memberId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByMember(memberId);
        return ResponseEntity.ok(reviews);
    }


}
