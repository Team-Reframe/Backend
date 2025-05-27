package backend.receipt.point.service;


import backend.receipt.point.dto.request.PointTotalRequest;
import backend.receipt.point.repository.PointRepository;
import backend.receipt.point.dto.response.PointTotalResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PointTotalService {


    private final PointRepository pointRepository;

    public PointTotalResponse getTotalPoints(PointTotalRequest request) {

        Integer total = pointRepository.getTotalPoints(request.getMemberId());
        return new PointTotalResponse(total != null ? total : 0);
    }

}
