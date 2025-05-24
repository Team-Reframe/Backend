package backend.receipt.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointResponse  {
    private Integer points;
    private Long purchaseId;
    private Long memberId;
}
