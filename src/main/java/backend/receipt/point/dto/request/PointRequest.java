package backend.receipt.point.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointRequest {
    private Integer amount;
    private Long purchaseId;
    private Long memberId;
}


