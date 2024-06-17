CREATE DATABASE `distribution`;
CREATE USER 'manager'@'localhost' IDENTIFIED BY 'manager';
GRANT ALL PRIVILEGES ON `distribution`.* TO 'manager'@'localhost';
FLUSH PRIVILEGES;