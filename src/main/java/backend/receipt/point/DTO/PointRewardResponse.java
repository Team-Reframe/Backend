package backend.receipt.point.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointRewardResponse {

    private int rewarded;      // 이번에 지급된 포인트 (조회 요청 시 0일 수 있음)
    private int totalPoints;   // 현재 누적 포인트 합계
}
