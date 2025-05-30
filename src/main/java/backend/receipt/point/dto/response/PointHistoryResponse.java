package backend.receipt.point.dto.response;

import java.time.LocalDateTime;

public class PointHistoryResponse {

    private final Long pointId;
    private final LocalDateTime createdAt;
    private final String type;
    private final int points;


    public PointHistoryResponse(Long pointId, LocalDateTime date, String type, int points) {
        this.pointId = pointId;
        this.createdAt = date;
        this.type = type;
        this.points = points;

    }

    public Long getPointId() {
        return pointId;
    }

    public LocalDateTime getDate() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }


}
