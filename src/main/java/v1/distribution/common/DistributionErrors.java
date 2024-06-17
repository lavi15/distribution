package v1.distribution.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum DistributionErrors {
    INTERNAL_SERVER_ERROR("서비스 내부 에러가 발생하였습니다."),
    INVALID_IMAGE_TYPE("잘못된 이미지 타입입니다."),
    PACKAGE_ID_NOT_FOUND("패키지 ID를 찾을 수 없습니다."),
    IMAGE_NOT_FOUND("이미지를 찾을 수 없습니다."),
    DUPLICATED_TRACKING_NO("중복된 운송 번호입니다.");

    String errorMsg;
}
