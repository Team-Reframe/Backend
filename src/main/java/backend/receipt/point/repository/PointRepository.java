package backend.receipt.point.repository;


import backend.receipt.point.domain.Point;
import backend.receipt.point.domain.PointType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface PointRepository extends CrudRepository<Point, Long> {

    @Query("SELECT SUM(p.points) FROM Point p WHERE p.member.id = :memberId AND p.type = :pointType")
    Integer getTotalPoints(@Param("memberId") Long memberId, @Param("pointType") PointType pointType);




}
