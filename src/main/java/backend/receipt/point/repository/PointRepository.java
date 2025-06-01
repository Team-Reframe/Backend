package backend.receipt.point.repository;


import backend.receipt.point.domain.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRepository extends CrudRepository<Point, Long> {

    @Query("SELECT SUM(p.points) FROM Point p WHERE p.member.id = :memberId")
    Integer getTotalPoints(@Param("memberId") Long memberId);


    List<Point> findByMemberId(Long memberId);

}
