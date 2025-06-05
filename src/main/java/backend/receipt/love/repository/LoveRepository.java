package backend.receipt.love.repository;

import backend.receipt.love.domain.Love;
import backend.receipt.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;



public interface LoveRepository extends JpaRepository<Love, Long> {

    Optional<Love> findByMemberIdAndStoreId(Long memberId, Long storeId);


    @Query("SELECT L FROM Love L WHERE L.memberId =:memberId")
    List<Love> getTotalLoves(@Param("memberId") Long memberId);






}
