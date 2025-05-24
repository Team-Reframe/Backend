package backend.receipt.point.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointResponse  {
    private Integer amount;
    private Long purchaseId;
    private Long memberId;   // 현재 누적 포인트 합계
}
