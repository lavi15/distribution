DROP TABLE IF EXISTS `distribution`.`delivery_package`;
CREATE TABLE `distribution`.`delivery_package`
(
    delivery_package_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tracking_no BIGINT NOT NULL UNIQUE
);

DROP TABLE IF EXISTS `distribution`.`image`;
CREATE TABLE `distribution`.`image`
(
    image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(100) NOT NULL,
    type VARCHAR(10) NOT NULL,
    delivery_package_id BIGINT,
    CONSTRAINT fk_delivery_package
        FOREIGN KEY (delivery_package_id)
            REFERENCES delivery_package(delivery_package_id)
            ON DELETE CASCADE
);