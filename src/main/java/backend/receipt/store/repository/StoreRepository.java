package backend.receipt.store.repository;

import backend.receipt.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.latitude BETWEEN :swLat AND :neLat AND s.longitude BETWEEN :swLng AND :neLng")
    List<Store> findByLocationInBounds(@Param("swLat") double swLat,
                                       @Param("neLat") double neLat,
                                       @Param("swLng") double swLng,
                                       @Param("neLng") double neLng);
}
