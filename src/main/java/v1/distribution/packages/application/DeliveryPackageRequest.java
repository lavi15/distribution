package v1.distribution.packages.application;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import v1.distribution.packages.domain.DeliveryPackage;

@Getter
@Schema(description = "패키지 요청")
public class DeliveryPackageRequest {

    @Schema(description = "운송 번호", example = "111122223333")
    private Long trackingNo;
    
    @Schema(description = "이미지")
    private List<ImageRequest> images;

    public DeliveryPackage toDomain() {
        DeliveryPackage deliveryPackage = DeliveryPackage.builder()
            .trackingNo(trackingNo)
            .images(images.stream().map(ImageRequest::toDomain).toList())
            .build();
        deliveryPackage.syncImagesWithPackage();

        return deliveryPackage;
    }

    @Builder
    public DeliveryPackageRequest(Long trackingNo, List<ImageRequest> images) {
        this.trackingNo = trackingNo;
        this.images = images;
    }
}
