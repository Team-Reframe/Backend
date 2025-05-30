package backend.receipt.point.dto.response;

import backend.receipt.point.domain.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointResponse  {
    private Integer points;
    private Long purchaseId;
    private Long memberId;
    private PointType pointType;
    private LocalDateTime createdAt;
}


