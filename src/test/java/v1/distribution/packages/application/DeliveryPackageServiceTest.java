package v1.distribution.packages.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.distribution.packages.domain.DeliveryPackageRepository;
import v1.distribution.packages.domain.ImageRepository;

@SpringBootTest
class DeliveryPackageServiceTest {
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DeliveryPackageService deliveryPackageService;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        deliveryPackageRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("패키지를 저장한 후 해당 패키지 dto를 반환한다.")
    void createPackage() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        //when
        DeliveryPackageDto deliveryPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //then
        assertThat(deliveryPackageDto).extracting("id", "trackingNo")
            .containsExactly(deliveryPackageDto.getId(), 1122L);

        assertThat(deliveryPackageDto.getImages()).extracting("filename", "type")
            .containsExactly(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG")
            );
    }

    @Test
    @DisplayName("모든 패키지를 조회하여 Dto 객체로 반환한다.")
    void getAllPackage() {
        //given
        List<ImageRequest> firstImageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest firstDeliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(firstImageRequests)
            .build();

        List<ImageRequest> secondImageRequests = Arrays.asList(
            ImageRequest.builder().filename("example3.png").type("PKG").build(),
            ImageRequest.builder().filename("example4.png").type("PKG").build()
        );

        DeliveryPackageRequest secondDeliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1123L)
            .images(secondImageRequests)
            .build();

        DeliveryPackageDto firstDeliveryPackageDto = deliveryPackageService.savePackage(firstDeliveryPackageRequest);

        DeliveryPackageDto secondDeliveryPackageDto = deliveryPackageService.savePackage(secondDeliveryPackageRequest);

        //when
        List<DeliveryPackageDto> deliveryPackageDtos = deliveryPackageService.getAllPackage();

        //then
        assertThat(deliveryPackageDtos).hasSize(2).extracting("id", "trackingNo")
            .containsExactly(
                tuple(firstDeliveryPackageDto.getId(), 1122L),
                tuple(secondDeliveryPackageDto.getId(), 1123L)
            );

        assertThat(deliveryPackageDtos.get(0).getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG")
            );

        assertThat(deliveryPackageDtos.get(1).getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example3.png", "PKG"),
                tuple("example4.png", "PKG")
            );
    }

    @Test
    @DisplayName("deliveryPackageId로 패키지를 조회하여 Dto 객체로 반환한다.")
    void getPackageById() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        DeliveryPackageDto seachedPackageDto = deliveryPackageService.getPackageById(savedPackageDto.getId());

        //then
        assertThat(seachedPackageDto).extracting("id", "trackingNo")
            .containsExactly(savedPackageDto.getId(), 1122L);

        assertThat(seachedPackageDto.getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG")
            );
    }

    @Test
    @DisplayName("trackingNo로 패키지를 조회하여 Dto 객체로 반환한다.")
    void getPackageByTrackingNo() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        DeliveryPackageDto seachedPackageDto = deliveryPackageService.getPackageByTrackingNo(1122L);

        //then
        assertThat(seachedPackageDto).extracting("id", "trackingNo")
            .containsExactly(savedPackageDto.getId(), 1122L);

        assertThat(seachedPackageDto.getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG")
            );
    }

    @Test
    @DisplayName("deliveryPackageId로 패키지를 삭제한다.")
    void deletePackage() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        deliveryPackageService.deletePackage(savedPackageDto.getId());

        //then
        List<DeliveryPackageDto> deliveryPackageDtos = deliveryPackageService.getAllPackage();

        assertThat(deliveryPackageDtos).isEmpty();
    }

    @Test
    @DisplayName("패키지의 trackingNo를 변경한다.")
    void updateTrackingNo() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        deliveryPackageService.updateTrackingNo(savedPackageDto.getId(), 1123L);

        //then
        DeliveryPackageDto updatedPackageDto = deliveryPackageService.getAllPackage().getFirst();

        assertThat(updatedPackageDto).extracting("id", "trackingNo")
            .containsExactly(savedPackageDto.getId(), 1123L);

        assertThat(updatedPackageDto.getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG")
            );
    }

    @Test
    @DisplayName("패키지의 이미지를 추가한다.")
    void addImage() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        ImageRequest addImageRequest = ImageRequest.builder().filename("example3.png").type("PKG").build();

        deliveryPackageService.addImage(savedPackageDto.getId(), addImageRequest);

        //then
        DeliveryPackageDto updatedPackageDto = deliveryPackageService.getAllPackage().getFirst();

        assertThat(updatedPackageDto).extracting("id", "trackingNo")
            .containsExactly(updatedPackageDto.getId(), 1122L);

        assertThat(updatedPackageDto.getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG"),
                tuple("example2.png", "PKG"),
                tuple("example3.png", "PKG")
            );
    }

    @Test
    @DisplayName("패키지의 이미지를 삭제한다.")
    void removeImage() {
        //given
        List<ImageRequest> imageRequests = Arrays.asList(
            ImageRequest.builder().filename("example1.png").type("PKG").build(),
            ImageRequest.builder().filename("example2.png").type("PKG").build()
        );

        DeliveryPackageRequest deliveryPackageRequest = DeliveryPackageRequest.builder()
            .trackingNo(1122L)
            .images(imageRequests)
            .build();

        DeliveryPackageDto savedPackageDto = deliveryPackageService.savePackage(deliveryPackageRequest);

        //when
        ImageRequest addImageRequest = ImageRequest.builder().filename("example2.png").type("PKG").build();

        deliveryPackageService.removeImage(savedPackageDto.getId(), addImageRequest);

        //then
        DeliveryPackageDto updatedPackageDto = deliveryPackageService.getAllPackage().getFirst();

        assertThat(updatedPackageDto).extracting("id", "trackingNo")
            .containsExactly(updatedPackageDto.getId(), 1122L);

        assertThat(updatedPackageDto.getImages()).extracting("filename", "type")
            .containsExactlyInAnyOrder(
                tuple("example1.png", "PKG")
            );
    }
}