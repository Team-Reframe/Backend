package backend.receipt.point.dto.response;

import backend.receipt.point.domain.Point;
import backend.receipt.point.domain.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointHistoryResponse {
    private LocalDateTime date;
    private PointType type;
    private int points;


    public static PointHistoryResponse from(Point point) {
        return new PointHistoryResponse(
                point.getCreatedAt(),
                point.getType(),
                point.getPoints()

        );
    }
}
