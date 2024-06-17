package v1.distribution.packages.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String filename;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @ManyToOne
    @JoinColumn(name = "delivery_package_id")
    private DeliveryPackage deliveryPackage;

    @Builder(toBuilder = true)
    public Image(Long imageId, String filename, ImageType type, DeliveryPackage deliveryPackage) {
        this.imageId = imageId;
        this.filename = filename;
        this.type = type;
        this.deliveryPackage = deliveryPackage;
    }

    public void updatePackage(DeliveryPackage deliveryPackage) {
        this.deliveryPackage = deliveryPackage;
    }
}
