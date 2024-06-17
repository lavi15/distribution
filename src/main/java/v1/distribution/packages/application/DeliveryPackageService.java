package v1.distribution.packages.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v1.distribution.packages.domain.DeliveryPackage;
import v1.distribution.packages.domain.DeliveryPackageRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryPackageService {
    DeliveryPackageRepository deliveryPackageRepository;
    DeliveryPackageReader deliveryPackageReader;

    @Transactional
    public DeliveryPackageDto savePackage(DeliveryPackageRequest deliveryPackageRequest) {
        DeliveryPackage deliveryPackage = deliveryPackageRequest.toDomain();
        return DeliveryPackageDto.fromDomain(deliveryPackageRepository.save(deliveryPackage));
    }

    @Transactional(readOnly = true)
    public List<DeliveryPackageDto> getAllPackage() {
        return deliveryPackageRepository.findAllWithImages().stream().map(DeliveryPackageDto::fromDomain).toList();
    }

    @Transactional(readOnly = true)
    public DeliveryPackageDto getPackageById(Long deliveryPackageId) {
        return deliveryPackageRepository.findByIdWithImages(deliveryPackageId)
            .map(DeliveryPackageDto::fromDomain)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public DeliveryPackageDto getPackageByTrackingNo(Long trackingNo) {
        return deliveryPackageRepository.findByTrackingNoWithImages(trackingNo)
            .map(DeliveryPackageDto::fromDomain)
            .orElse(null);
    }

    @Transactional
    public void deletePackage(Long deliveryPackageId) {
        deliveryPackageRepository.deleteById(deliveryPackageId);
    }

    @Transactional
    public DeliveryPackageDto updateTrackingNo(Long deliveryPackageId, Long newTrackingNo) {
        DeliveryPackage updatedPackage = deliveryPackageReader.read(deliveryPackageId);
        updatedPackage.updateTrackingNo(newTrackingNo);

        return DeliveryPackageDto.fromDomain(deliveryPackageRepository.save(updatedPackage));
    }

    @Transactional
    public DeliveryPackageDto addImage(Long deliveryPackageId, ImageRequest imageRequest) {
        DeliveryPackage updatedPackage = deliveryPackageReader.read(deliveryPackageId);
        updatedPackage.addImage(imageRequest);

        return DeliveryPackageDto.fromDomain(deliveryPackageRepository.save(updatedPackage));
    }

    @Transactional
    public DeliveryPackageDto removeImage(Long deliveryPackageId, ImageRequest imageRequest) {
        DeliveryPackage updatedPackage = deliveryPackageReader.read(deliveryPackageId);
        updatedPackage.removeImage(imageRequest);

        return DeliveryPackageDto.fromDomain(deliveryPackageRepository.save(updatedPackage));
    }
}
