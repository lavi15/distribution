package v1.distribution.packages.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import v1.distribution.packages.domain.Image;

@Getter
@Schema(description = "이미지")
public class ImageDto {

    @Schema(description = "파일명", example = "example.png")
    private String filename;

    @Schema(description = "파일 타입", example = "PKG, LBL, QR,...")
    private String type;

    @Builder
    public ImageDto(String filename, String type) {
        this.filename = filename;
        this.type = type;
    }

    public static ImageDto fromDomain(Image image) {
        return ImageDto.builder()
            .filename(image.getFilename())
            .type(image.getType().getDisplayName())
            .build();
    }
}
