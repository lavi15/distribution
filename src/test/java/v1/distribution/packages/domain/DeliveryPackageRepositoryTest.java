package v1.distribution.packages.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeliveryPackageRepositoryTest {
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        deliveryPackageRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("패키지를 저장한다.")
    void createPackage() {
        //given
        List<Image> images = Arrays.asList(
            Image.builder().filename("example1.png").type(ImageType.PKG).build(),
            Image.builder().filename("example2.png").type(ImageType.PKG).build()
        );

        DeliveryPackage deliveryPackage = DeliveryPackage.builder()
            .trackingNo(1122L)
            .images(images)
            .build();

        deliveryPackage.syncImagesWithPackage();

        //when
        deliveryPackageRepository.save(deliveryPackage);

        DeliveryPackage savedPackage = deliveryPackageRepository.findAllWithImages().getFirst();
        List<Image> savedImage = savedPackage.getImages();

        //then
        assertThat(savedPackage.getTrackingNo()).isEqualTo(1122L);
        assertThat(savedImage).extracting("filename", "type")
            .containsExactly(
                tuple("example1.png", ImageType.PKG),
                tuple("example2.png", ImageType.PKG)
            );
    }

    @Test
    @DisplayName("deliveryPackageId로 패키지를 조회한다.")
    void findByIdWithImages() {
        //given
        List<Image> images = Arrays.asList(
            Image.builder().filename("example1.png").type(ImageType.PKG).build(),
            Image.builder().filename("example2.png").type(ImageType.PKG).build()
        );

        DeliveryPackage deliveryPackage = DeliveryPackage.builder()
            .trackingNo(1122L)
            .images(images)
            .build();

        deliveryPackage.syncImagesWithPackage();

        //when
        DeliveryPackage savedPackage = deliveryPackageRepository.save(deliveryPackage);

        DeliveryPackage searchedPackage = deliveryPackageRepository.findByIdWithImages(savedPackage.getDeliveryPackageId()).orElse(null);

        //then
        assertThat(searchedPackage).extracting("deliveryPackageId", "trackingNo")
            .containsExactly(savedPackage.getDeliveryPackageId(), 1122L);

        assertThat(searchedPackage.getImages()).extracting("filename", "type")
            .containsExactly(
                tuple("example1.png", ImageType.PKG),
                tuple("example2.png", ImageType.PKG)
            );
    }

    @Test
    @DisplayName("trackingNo로 패키지를 조회한다.")
    void findByTrackingNoWithImages() {
        //given
        List<Image> images = Arrays.asList(
            Image.builder().filename("example1.png").type(ImageType.PKG).build(),
            Image.builder().filename("example2.png").type(ImageType.PKG).build()
        );

        DeliveryPackage deliveryPackage = DeliveryPackage.builder()
            .trackingNo(1122L)
            .images(images)
            .build();

        deliveryPackage.syncImagesWithPackage();

        //when
        deliveryPackageRepository.save(deliveryPackage);

        DeliveryPackage searchedPackage = deliveryPackageRepository.findByTrackingNoWithImages(1122L).orElse(null);

        //then
        assertThat(searchedPackage).extracting("deliveryPackageId", "trackingNo")
            .containsExactly(searchedPackage.getDeliveryPackageId(), 1122L);

        assertThat(searchedPackage.getImages()).extracting("filename", "type")
            .containsExactly(
                tuple("example1.png", ImageType.PKG),
                tuple("example2.png", ImageType.PKG)
            );
    }

    @Test
    @DisplayName("deliveryPackageId로 패키지를 삭제한다.")
    void deleteById() {
        //given
        DeliveryPackage deliveryPackage = DeliveryPackage.builder()
            .trackingNo(1122L)
            .images(null)
            .build();

        //when
        DeliveryPackage savedPackage = deliveryPackageRepository.save(deliveryPackage);

        deliveryPackageRepository.deleteById(savedPackage.getDeliveryPackageId());

        List<DeliveryPackage> packages = deliveryPackageRepository.findAll();

        //then
        assertThat(packages).isEmpty();
    }
}