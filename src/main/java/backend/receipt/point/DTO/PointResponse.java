package backend.receipt.point.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointResponse  {
    private Integer points;
    private Long purchaseId;
    private Long memberId;
}
