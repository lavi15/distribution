package v1.distribution.packages.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import v1.distribution.packages.domain.Image;
import v1.distribution.packages.domain.ImageType;

@Getter
@Schema(description = "이미지 요청")
public class ImageRequest {

    @Schema(description = "파일명", example = "example.png")
    private String filename;

    @Schema(description = "파일 타입", example = "PKG, LBL, QR,...")
    private String type;

    @Builder
    public ImageRequest(String filename, String type) {
        this.filename = filename;
        this.type = type;
    }

    public Image toDomain() {
        return Image.builder()
            .filename(filename)
            .type(ImageType.fromDisplayName(type))
            .build();
    }
}
