package backend.receipt.point.service;

import backend.receipt.point.domain.Point;
import backend.receipt.point.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
public class PointDeleteService {

    private final PointRepository pointRepository;

    public PointDeleteService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void deletePointById(Long pointId, Long memberId) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new IllegalArgumentException("포인트 내역이 존재하지 않습니다."));

        if (!point.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 포인트는 요청한 멤버의 것이 아닙니다.");
        }

        pointRepository.delete(point);
    }
}
