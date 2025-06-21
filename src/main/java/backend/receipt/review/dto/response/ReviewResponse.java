package backend.receipt.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {


        private Long reviewId;
        private String content;
        private int rating;
        private String createdAt;
}
