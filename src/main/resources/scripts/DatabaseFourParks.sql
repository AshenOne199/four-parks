/* ---------------------------------------------------- */
/*  Generated by Enterprise Architect Version 15.0 		*/
/*  Created On : 05-May-2024 1:37:16 pm 				*/
/*  DBMS       : MySql 						*/
/* ---------------------------------------------------- */

SET FOREIGN_KEY_CHECKS=0
;
/* Drop Tables */

DROP TABLE IF EXISTS `city` CASCADE
;

DROP TABLE IF EXISTS `credit_card` CASCADE
;

DROP TABLE IF EXISTS `invoice` CASCADE
;

DROP TABLE IF EXISTS `location` CASCADE
;

DROP TABLE IF EXISTS `opening_hours` CASCADE
;

DROP TABLE IF EXISTS `parking` CASCADE
;

DROP TABLE IF EXISTS `parking_rate` CASCADE
;

DROP TABLE IF EXISTS `parking_slot` CASCADE
;

DROP TABLE IF EXISTS `parking_type` CASCADE
;

DROP TABLE IF EXISTS `permission` CASCADE
;

DROP TABLE IF EXISTS `reservation` CASCADE
;

DROP TABLE IF EXISTS `role` CASCADE
;

DROP TABLE IF EXISTS `role_permission` CASCADE
;

DROP TABLE IF EXISTS `user` CASCADE
;

DROP TABLE IF EXISTS `user_role` CASCADE
;

DROP TABLE IF EXISTS `vehicle_type` CASCADE
;

/* Create Tables */

CREATE TABLE `city`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    CONSTRAINT `PK_city` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `credit_card`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `card_number` VARCHAR(16) NOT NULL,
    `expiration_date` VARCHAR(10) NOT NULL,
    `cvv` VARCHAR(4) NOT NULL,
    CONSTRAINT `PK_Table1` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `invoice`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `invoice_time` DATE NOT NULL,
    `details` VARCHAR(255) NULL,
    `total` DECIMAL(10,2) NOT NULL,
    CONSTRAINT `PK_invoice` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `location`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `city_id` BIGINT NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `geolocation` POINT NOT NULL,
    CONSTRAINT `PK_location` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `opening_hours`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `open_time` DATE NOT NULL,
    `close_time` DATE NOT NULL,
    CONSTRAINT `PK_opening_hours` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `parking`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `admin_id` BIGINT NOT NULL,
    `location_id` BIGINT NOT NULL,
    `parking_type_id` BIGINT NOT NULL,
    `opening_hours_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `total_slots` INT NOT NULL,
    `available_slots` INT NOT NULL,
    `loyalty` BOOL NOT NULL,
    CONSTRAINT `PK_parking` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `parking_rate`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `parking_id` BIGINT NOT NULL,
    `vehicle_type_id` BIGINT NOT NULL,
    `rate` DECIMAL(10,2) NOT NULL,
    CONSTRAINT `PK_parking_rate` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `parking_slot`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `parking_id` BIGINT NOT NULL,
    `vehicle_type_id` BIGINT NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    CONSTRAINT `PK_parking_slot` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `parking_type`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `type` VARCHAR(50) NOT NULL,
    CONSTRAINT `PK_parking_type` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `permission`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `permission_name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    CONSTRAINT `PK_permissions` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `reservation`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `slot_id` BIGINT NOT NULL,
    `vehicle_type_id` BIGINT NOT NULL,
    `invoice_id` BIGINT NULL,
    `reservation_time` DATE NOT NULL,
    `reservation_start_time` DATE NULL,
    `reservation_end_time` DATE NULL,
    CONSTRAINT `PK_reservation` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `role`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `role_name` enum('GERENTE','ADMINISTRADOR','USUARIO') DEFAULT NULL,
    CONSTRAINT `PK_roles` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `role_permission`
(
    `role_id` BIGINT NOT NULL,
    `permission_id` BIGINT NOT NULL,
    CONSTRAINT `PK_role_permissions` PRIMARY KEY (`role_id` ASC, `permission_id` ASC)
)

;

CREATE TABLE `user`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `ip` VARCHAR(45) NOT NULL,
    `first_name` VARCHAR(100) NOT NULL,
    `second_name` VARCHAR(100) NULL,
    `first_lastname` VARCHAR(100) NOT NULL,
    `second_lastname` VARCHAR(100) NOT NULL,
    `account_active` BOOL NOT NULL DEFAULT false,
    `account_blocked` BOOL NOT NULL DEFAULT false,
    `login_attempts` INT NOT NULL DEFAULT 0,
    `created_at` DATE DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATE DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `PK_users` PRIMARY KEY (`id` ASC)
)

;

CREATE TABLE `user_role`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    CONSTRAINT `PK_user_roles` PRIMARY KEY (`user_id` ASC, `role_id` ASC)
)

;

CREATE TABLE `vehicle_type`
(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `type` VARCHAR(100) NOT NULL,
    CONSTRAINT `PK_vehicle` PRIMARY KEY (`id` ASC)
)

;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE `credit_card`
    ADD INDEX `IXFK_credit_cards_users` (`user_id` ASC)
;

ALTER TABLE `location`
    ADD INDEX `IXFK_location_city` (`city_id` ASC)
;

ALTER TABLE `parking`
    ADD INDEX `IXFK_parking_location` (`location_id` ASC)
;

ALTER TABLE `parking`
    ADD INDEX `IXFK_parking_opening_hours` (`opening_hours_id` ASC)
;

ALTER TABLE `parking`
    ADD INDEX `IXFK_parking_parking_type` (`parking_type_id` ASC)
;

ALTER TABLE `parking`
    ADD INDEX `IXFK_parking_user` (`admin_id` ASC)
;

ALTER TABLE `parking_rate`
    ADD INDEX `IXFK_parking_rate_parking` (`parking_id` ASC)
;

ALTER TABLE `parking_rate`
    ADD INDEX `IXFK_parking_rate_vehicle_type` (`vehicle_type_id` ASC)
;

ALTER TABLE `parking_slot`
    ADD INDEX `IXFK_parking_slot_parking` (`parking_id` ASC)
;

ALTER TABLE `parking_slot`
    ADD INDEX `IXFK_parking_slot_vehicle_type` (`vehicle_type_id` ASC)
;

ALTER TABLE `reservation`
    ADD INDEX `IXFK_reservation_invoice` (`invoice_id` ASC)
;

ALTER TABLE `reservation`
    ADD INDEX `IXFK_reservation_parking_slot` (`slot_id` ASC)
;

ALTER TABLE `reservation`
    ADD INDEX `IXFK_reservation_users` (`user_id` ASC)
;

ALTER TABLE `reservation`
    ADD INDEX `IXFK_reservation_vehicle` (`vehicle_type_id` ASC)
;

ALTER TABLE `role_permission`
    ADD INDEX `IXFK_role_permissions_permissions` (`permission_id` ASC)
;

ALTER TABLE `role_permission`
    ADD INDEX `IXFK_role_permissions_roles` (`role_id` ASC)
;

ALTER TABLE `user_role`
    ADD INDEX `IXFK_user_roles_roles` (`role_id` ASC)
;

ALTER TABLE `user_role`
    ADD INDEX `IXFK_user_roles_users` (`user_id` ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE `credit_card`
    ADD CONSTRAINT `FK_credit_cards_users`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `location`
    ADD CONSTRAINT `FK_location_city`
        FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking`
    ADD CONSTRAINT `FK_parking_location`
        FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking`
    ADD CONSTRAINT `FK_parking_opening_hours`
        FOREIGN KEY (`opening_hours_id`) REFERENCES `opening_hours` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking`
    ADD CONSTRAINT `FK_parking_parking_type`
        FOREIGN KEY (`parking_type_id`) REFERENCES `parking_type` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking`
    ADD CONSTRAINT `FK_parking_user`
        FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking_rate`
    ADD CONSTRAINT `FK_parking_rate_parking`
        FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking_rate`
    ADD CONSTRAINT `FK_parking_rate_vehicle_type`
        FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking_slot`
    ADD CONSTRAINT `FK_parking_slot_parking`
        FOREIGN KEY (`parking_id`) REFERENCES `parking` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `parking_slot`
    ADD CONSTRAINT `FK_parking_slot_vehicle_type`
        FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `reservation`
    ADD CONSTRAINT `FK_reservation_invoice`
        FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `reservation`
    ADD CONSTRAINT `FK_reservation_parking_slot`
        FOREIGN KEY (`slot_id`) REFERENCES `parking_slot` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `reservation`
    ADD CONSTRAINT `FK_reservation_users`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `role_permission`
    ADD CONSTRAINT `FK_role_permissions_permissions`
        FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `role_permission`
    ADD CONSTRAINT `FK_role_permissions_roles`
        FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `user_role`
    ADD CONSTRAINT `FK_user_roles_roles`
        FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `user_role`
    ADD CONSTRAINT `FK_user_roles_users`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

SET FOREIGN_KEY_CHECKS=1
;
