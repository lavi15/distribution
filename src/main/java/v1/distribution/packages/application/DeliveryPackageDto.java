package v1.distribution.packages.application;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import v1.distribution.packages.domain.DeliveryPackage;

@Getter
@Schema(description = "패키지")
public class DeliveryPackageDto {

    @Schema(description = "패키지 id", example = "1")
    private Long id;

    @Schema(description = "운송 번호", example = "111122223333")
    private Long trackingNo;
    
    @Schema(description = "이미지")
    private List<ImageDto> images;

    @Builder
    public DeliveryPackageDto(Long id, Long trackingNo, List<ImageDto> images) {
        this.id = id;
        this.trackingNo = trackingNo;
        this.images = images;
    }

    public static DeliveryPackageDto fromDomain(DeliveryPackage deliveryPackage) {
        return DeliveryPackageDto.builder()
            .id(deliveryPackage.getDeliveryPackageId())
            .trackingNo(deliveryPackage.getTrackingNo())
            .images(deliveryPackage.getImages().stream().map(ImageDto::fromDomain).toList())
            .build();
    }
}
