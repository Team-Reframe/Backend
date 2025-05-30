package backend.receipt.point.service;

import backend.receipt.point.domain.Point;
import backend.receipt.point.dto.response.PointHistoryResponse;
import backend.receipt.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor

public class PointHistoryService {

    private final PointRepository pointRepository;

    public List<PointHistoryResponse> getHistory(Long memberId) {
        List<Point> list = pointRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        return list.stream()
                .map(PointHistoryResponse::from)
                .collect(Collectors.toList());
    }
}
