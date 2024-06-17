package v1.distribution.packages.domain;

import static v1.distribution.common.DistributionErrors.IMAGE_NOT_FOUND;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v1.distribution.common.DistributionNotFoundException;
import v1.distribution.packages.application.ImageRequest;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "delivery_package")
public class DeliveryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPackageId;

    @Column(nullable = false, unique = true)
    private Long trackingNo;

    @OneToMany(mappedBy = "deliveryPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @Builder(toBuilder = true)
    public DeliveryPackage(Long deliveryPackageId, Long trackingNo, List<Image> images) {
        this.deliveryPackageId = deliveryPackageId;
        this.trackingNo = trackingNo;
        this.images = images;
    }

    public void updateTrackingNo(Long newTrackingNo) {
        this.trackingNo = newTrackingNo;
    }

    public void syncImagesWithPackage() {
        for(Image image : images) {
            image.updatePackage(this);
        }
    }

    public void addImage(ImageRequest imageRequest) {
        this.images.add(Image.builder()
                .filename(imageRequest.getFilename())
                .type(ImageType.fromDisplayName(imageRequest.getType()))
                .deliveryPackage(this)
                .build());
    }

    public void removeImage(ImageRequest imageRequest) {
        Image imageToRemove = this.images.stream()
            .filter(image -> image.getFilename().equals(imageRequest.getFilename())
                && image.getType() == ImageType.fromDisplayName(imageRequest.getType()))
            .findFirst()
            .orElseThrow(() -> new DistributionNotFoundException(IMAGE_NOT_FOUND));

        this.images.remove(imageToRemove);
    }
}