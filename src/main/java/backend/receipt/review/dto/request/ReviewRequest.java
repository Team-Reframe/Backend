package backend.receipt.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewRequest {

        private Long memberId;
        private Long purchaseId;
        private String content;
        private int rating; // 별점 1~5
}
