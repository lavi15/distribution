package v1.distribution.packages.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Long> {
    @Query("SELECT dp FROM DeliveryPackage dp LEFT JOIN FETCH dp.images")
    List<DeliveryPackage> findAllWithImages();

    @Query("SELECT dp FROM DeliveryPackage dp LEFT JOIN FETCH dp.images WHERE dp.deliveryPackageId = :deliveryPackageId")
    Optional<DeliveryPackage> findByIdWithImages(@Param("deliveryPackageId") Long deliveryPackageId);

    @Query("SELECT dp FROM DeliveryPackage dp LEFT JOIN FETCH dp.images WHERE dp.trackingNo = :trackingNo")
    Optional<DeliveryPackage> findByTrackingNoWithImages(@Param("trackingNo") Long trackingNo);
}
