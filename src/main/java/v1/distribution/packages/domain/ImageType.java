package v1.distribution.packages.domain;

import static v1.distribution.common.DistributionErrors.INVALID_IMAGE_TYPE;

import lombok.Getter;
import v1.distribution.common.DistributionBadRequestException;

@Getter
public enum ImageType {
    PKG("PKG", "Package"),
    LBL("LBL", "Label"),
    QR("QR", "QR Code");

    private final String displayName;
    private final String description;

    ImageType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public static ImageType fromDisplayName(String displayName) {
        for (ImageType type : ImageType.values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }

        throw new DistributionBadRequestException(INVALID_IMAGE_TYPE);
    }
}
