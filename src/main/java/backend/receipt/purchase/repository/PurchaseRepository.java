package backend.receipt.purchase.repository;

import backend.receipt.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // 추가 로직이 생기면 나중에 작성
}