package v1.distribution.packages.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import v1.distribution.packages.application.DeliveryPackageDto;
import v1.distribution.packages.application.DeliveryPackageRequest;
import v1.distribution.packages.application.DeliveryPackageService;
import v1.distribution.packages.application.ImageRequest;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryPackageController {
    DeliveryPackageService deliveryPackageService;

    @Operation(summary = "패키지를 저장한다.")
    @PostMapping("/packages")
    public DeliveryPackageDto savePackage(@RequestBody DeliveryPackageRequest deliveryPackageRequest) {
        return deliveryPackageService.savePackage(deliveryPackageRequest);
    }

    @Operation(summary = "전체 패키지를 조회한다.")
    @GetMapping("/packages")
    public List<DeliveryPackageDto> getAllPackage() {
        return deliveryPackageService.getAllPackage();
    }

    @Operation(summary = "id로 패키지를 조회한다.")
    @GetMapping("/packages/{id}")
    public DeliveryPackageDto getPackageById(@PathVariable Long id) {
        return deliveryPackageService.getPackageById(id);
    }

    @Operation(summary = "운송 번호로 패키지를 조회한다.")
    @GetMapping("/packages/tracking_no/{trackingNo}")
    public DeliveryPackageDto getPackageByTrackingNo(@PathVariable Long trackingNo) {
        return deliveryPackageService.getPackageByTrackingNo(trackingNo);
    }

    @Operation(summary = "id로 패키지를 삭제한다.")
    @DeleteMapping("/packages/{id}")
    public void deletePackage(@PathVariable Long id) {
        deliveryPackageService.deletePackage(id);
    }

    @Operation(summary = "패키지의 운송 번호를 변경한다.")
    @PatchMapping("/packages/{id}/trackingNo")
    public DeliveryPackageDto updateTrackingNo(@PathVariable Long id, @RequestBody Long trackingNo) {
        return deliveryPackageService.updateTrackingNo(id, trackingNo);
    }

    @Operation(summary = "패키지의 이미지를 추가한다.")
    @PostMapping("/packages/{id}/image")
    public DeliveryPackageDto addImage(@PathVariable Long id, @RequestBody ImageRequest image) {
        return deliveryPackageService.addImage(id, image);
    }

    @Operation(summary = "패키지의 이미지를 삭제한다.")
    @DeleteMapping("/packages/{id}/image")
    public DeliveryPackageDto removeImage(@PathVariable Long id, @RequestBody ImageRequest image) {
        return deliveryPackageService.removeImage(id, image);
    }
}
