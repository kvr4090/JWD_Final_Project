-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ts-db-v5
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ts-db-v5
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ts-db-v2` DEFAULT CHARACTER SET utf8 ;
USE `ts-db-v2` ;

-- -----------------------------------------------------
-- Table `ts-db-v5`.`roles`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `type_UNIQUE` (`type` ASC) VISIBLE)
ENGINE = InnoDB;
INSERT INTO `ts-db-v2`.`roles` (`id`,`type`) VALUES (1,'GUEST');
INSERT INTO `ts-db-v2`.`roles` (`id`,`type`) VALUES (2,'DRIVER');
INSERT INTO `ts-db-v2`.`roles` (`id`,`type`) VALUES (3,'LOGIST');
INSERT INTO `ts-db-v2`.`roles` (`id`,`type`) VALUES (4,'MECHANIC');
INSERT INTO `ts-db-v2`.`roles` (`id`,`type`) VALUES (5,'ADMIN');
-- -----------------------------------------------------
-- Table `ts-db-v5`.`requirements`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`requirements` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `weight` DECIMAL(4,2) NULL,
  `volume` DECIMAL(4,2) NULL,
  `pallets_quantity` INT NULL,
  `length` DECIMAL(4,2) NULL,
  `width` DECIMAL(4,2) NULL,
  `height` DECIMAL(4,2) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`vehicle_types`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`vehicle_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `requirements_id` INT NOT NULL,
  `type` VARCHAR(80) NULL,
  `is_available` TINYINT NULL,
  PRIMARY KEY (`id`, `requirements_id`),
  INDEX `fk_vehicle_types_requirements1_idx` (`requirements_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_vehicle_types_requirements1`
    FOREIGN KEY (`requirements_id`)
    REFERENCES `ts-db-v2`.`requirements` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`vehicles`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`vehicles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `vehicle_types_id` INT NOT NULL,
  `trailer_id` INT NULL,
  `license_plate` VARCHAR(7) NULL,
  `odometer` MEDIUMINT(7) NULL,
  `is_available` TINYINT NULL,
  `drive_license` SET('C', 'CE') NULL,
  `model` VARCHAR(20) NULL,
  `brand` VARCHAR(20) NULL,
  `year_of_issue` DATE NULL,
  `color` VARCHAR(20) NULL,
  `note` VARCHAR(500) NULL,
  PRIMARY KEY (`id`, `vehicle_types_id`),
  INDEX `fk_vehicles_transport_types1_idx` (`vehicle_types_id` ASC) VISIBLE,
  UNIQUE INDEX `license_plate_UNIQUE` (`license_plate` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_vehicles_transport_types1`
    FOREIGN KEY (`vehicle_types_id`)
    REFERENCES `ts-db-v2`.`vehicle_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`users`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roles_id` INT NOT NULL,
  `vehicles_id` INT NULL,
  `login` VARCHAR(20) NULL,
  `correct_hash` VARCHAR(71) NULL,
  `email` VARCHAR(64) NULL,
  `phone` VARCHAR(12) NULL,
  `is_active` TINYINT NULL,
  PRIMARY KEY (`id`, `roles_id`),
  INDEX `fk_users_roles1_idx` (`roles_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_users_vehicles1_idx` (`vehicles_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `ts-db-v2`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_vehicles1`
    FOREIGN KEY (`vehicles_id`)
    REFERENCES `ts-db-v2`.`vehicles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`trips`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`trips` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `vehicles_id` INT NOT NULL,
  `start_date` DATE NULL,
  `finish_date` DATE NULL,
  `note` VARCHAR(500) NULL,
  PRIMARY KEY (`id`, `vehicles_id`),
  INDEX `fk_trips_vehicles1_idx` (`vehicles_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_trips_vehicles1`
    FOREIGN KEY (`vehicles_id`)
    REFERENCES `ts-db-v2`.`vehicles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`shippers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ts-db-v2`.`shippers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  `email` VARCHAR(50) NULL,
  `contact_person_name` VARCHAR(20) NULL,
  `contact_person_surname` VARCHAR(20) NULL,
  `contact_phone` VARCHAR(12) NULL,
  `note` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ts-db-v2`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `users_id` INT NOT NULL,
  `shippers_id` INT NOT NULL,
  `requirements_id` INT NOT NULL,
  `trips_id` INT NULL,
  `date` DATE NULL,
  `price` DECIMAL NULL,
  `route_start_point` VARCHAR(50) NULL,
  `route_end_point` VARCHAR(50) NULL,
  `route_distance` INT NULL,
  `note` VARCHAR(500) NULL,
  PRIMARY KEY (`id`, `users_id`, `shippers_id`, `requirements_id`),
  INDEX `fk_orders_shippers1_idx` (`shippers_id` ASC) VISIBLE,
  INDEX `fk_orders_requirements1_idx` (`requirements_id` ASC) VISIBLE,
  INDEX `fk_orders_trips1_idx` (`trips_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `ts-db-v2`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_shippers1`
    FOREIGN KEY (`shippers_id`)
    REFERENCES `ts-db-v2`.`shippers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_requirements1`
    FOREIGN KEY (`requirements_id`)
    REFERENCES `ts-db-v2`.`requirements` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_trips1`
    FOREIGN KEY (`trips_id`)
    REFERENCES `ts-db-v2`.`trips` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`personal_data`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`personal_data` (
  `users_id` INT NOT NULL,
  `name` VARCHAR(20) NULL,
  `surname` VARCHAR(20) NULL,
  `birthdate` DATE NULL,
  `post` VARCHAR(20) NULL,
  `passport_number` VARCHAR(9) NULL,
  `passport_identification_number` VARCHAR(14) NULL,
  `passport_date_of_expiry` DATE NULL,
  `passport_date_of_issue` DATE NULL,
  `passport_authority` VARCHAR(50) NULL,
  `drive_license` SET('C', 'CE') NULL,
  `recruitment_date` DATE NULL,
  `termination_date` DATE NULL,
  PRIMARY KEY (`users_id`),
  UNIQUE INDEX `passport_number_UNIQUE` (`passport_number` ASC) VISIBLE,
  UNIQUE INDEX `passport_identification_number_UNIQUE` (`passport_identification_number` ASC) VISIBLE,
  CONSTRAINT `fk_personal_data_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `ts-db-v2`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ts-db-v5`.`trips_has_users`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ts-db-v2`.`trips_has_users` (
  `trips_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  PRIMARY KEY (`trips_id`, `users_id`),
  INDEX `fk_trips_has_users_users1_idx` (`users_id` ASC) VISIBLE,
  INDEX `fk_trips_has_users_trips1_idx` (`trips_id` ASC) VISIBLE,
  CONSTRAINT `fk_trips_has_users_trips1`
    FOREIGN KEY (`trips_id`)
    REFERENCES `ts-db-v2`.`trips` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trips_has_users_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `ts-db-v2`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
