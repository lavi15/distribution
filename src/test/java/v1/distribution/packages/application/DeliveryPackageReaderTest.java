package v1.distribution.packages.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.distribution.common.DistributionNotFoundException;
import v1.distribution.packages.domain.DeliveryPackage;
import v1.distribution.packages.domain.DeliveryPackageRepository;
import v1.distribution.packages.domain.Image;
import v1.distribution.packages.domain.ImageRepository;
import v1.distribution.packages.domain.ImageType;

@SpringBootTest
class DeliveryPackageReaderTest {
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DeliveryPackageReader deliveryPackageReader;

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        deliveryPackageRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("deliveryPackageId로 패키지를 조회하여 반환한다.")
    void read() {
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

        DeliveryPackage searchedPackage = deliveryPackageReader.read(savedPackage.getDeliveryPackageId());

        //then
        assertThat(searchedPackage.getTrackingNo()).isEqualTo(1122L);

        assertThat(searchedPackage.getImages()).extracting("filename", "type")
            .containsExactly(
                tuple("example1.png", ImageType.PKG),
                tuple("example2.png", ImageType.PKG)
            );
    }

    @Test
    @DisplayName("존재하지 않는 deliveryPackageId로 조회시 DistributionNotFoundException이 발생한다.")
    void readWithException() {
        //given //when //then
        assertThrows(DistributionNotFoundException.class, () -> {
            deliveryPackageReader.read(0L);
        });
    }
}