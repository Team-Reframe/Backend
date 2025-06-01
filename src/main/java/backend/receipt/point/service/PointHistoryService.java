package backend.receipt.point.service;

import backend.receipt.point.domain.Point;
import backend.receipt.point.dto.response.PointHistoryResponse;
import backend.receipt.point.repository.PointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointHistoryService {

    private final PointRepository pointRepository;

    public PointHistoryService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public List<PointHistoryResponse> getPointHistoryByMemberId(Long memberId) {
        List<Point> points = pointRepository.findByMemberId(memberId);

        return points.stream()
                .map(point -> new PointHistoryResponse(
                        point.getId(),
                        point.getCreatedAt(),
                        point.getType().name(),
                        point.getPoints()
                ))
                .toList();
    }
}
