package backend.receipt.review.repository;

import backend.receipt.review.domain.Review;
import backend.receipt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId")
    List<Review> findAllByStoreId(@Param("storeId") Long storeId);

    List<Review> findByMember(Member member);


}
