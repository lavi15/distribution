package v1.distribution.packages.application;

import static v1.distribution.common.DistributionErrors.PACKAGE_ID_NOT_FOUND;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import v1.distribution.common.DistributionNotFoundException;
import v1.distribution.packages.domain.DeliveryPackage;
import v1.distribution.packages.domain.DeliveryPackageRepository;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryPackageReader {
    DeliveryPackageRepository deliveryPackageRepository;

    public DeliveryPackage read(Long deliveryPackageId) {
        return deliveryPackageRepository.findByIdWithImages(deliveryPackageId)
            .orElseThrow(() -> new DistributionNotFoundException(PACKAGE_ID_NOT_FOUND));
    }
}
