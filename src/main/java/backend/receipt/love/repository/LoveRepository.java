package backend.receipt.love.repository;

import backend.receipt.love.domain.Love;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {

    Optional<Love> findByMemberIdAndStoreId(Long memberId, Long storeId);
    void deleteByMemberIdAndStoreId(Long memberId, Long storeId);

}
