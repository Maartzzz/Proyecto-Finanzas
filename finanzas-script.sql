-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema finanzas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finanzas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finanzas` DEFAULT CHARACTER SET utf8mb3 ;
USE `finanzas` ;

-- -----------------------------------------------------
-- Table `finanzas`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`usuarios` (
  `usu_id` INT NOT NULL AUTO_INCREMENT,
  `usu_nombre` VARCHAR(255) NULL DEFAULT NULL,
  `usu_apellido` VARCHAR(255) NULL DEFAULT NULL,
  `usu_correo` VARCHAR(255) NULL DEFAULT NULL,
  `usu_telefono` VARCHAR(255) NULL DEFAULT NULL,
  `usu_contrasena` VARCHAR(255) NULL DEFAULT NULL,
  `usu_fechareg` DATE NOT NULL,
  `rol` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`usu_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `finanzas`.`metas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`metas` (
  `met_id` INT NOT NULL AUTO_INCREMENT,
  `met_titulo` VARCHAR(255) NULL DEFAULT NULL,
  `met_objetivo` DOUBLE NULL DEFAULT NULL,
  `met_limite` DATE NOT NULL,
  `usu_id` INT NOT NULL,
  `met_activo` BIT(1) NULL DEFAULT NULL,
  `met_acumulado` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`met_id`),
  INDEX `fk_metas_usuarios1_idx` (`usu_id` ASC) VISIBLE,
  CONSTRAINT `fk_metas_usuarios1`
    FOREIGN KEY (`usu_id`)
    REFERENCES `finanzas`.`usuarios` (`usu_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `finanzas`.`tipo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`tipo` (
  `tipo_id` INT NOT NULL,
  `tipo_nombre` VARCHAR(255) NULL DEFAULT NULL,
  `tipo_color` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`tipo_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `finanzas`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`categoria` (
  `cat_id` INT NOT NULL AUTO_INCREMENT,
  `cat_nombre` VARCHAR(255) NULL DEFAULT NULL,
  `cat_descripcion` VARCHAR(255) NULL DEFAULT NULL,
  `usu_id` INT NOT NULL,
  `tipo_id` INT NOT NULL,
  PRIMARY KEY (`cat_id`),
  INDEX `fk_categoria_usuarios_idx` (`usu_id` ASC) VISIBLE,
  INDEX `fk_categoria_tipo1_idx` (`tipo_id` ASC) VISIBLE,
  CONSTRAINT `fk_categoria_tipo1`
    FOREIGN KEY (`tipo_id`)
    REFERENCES `finanzas`.`tipo` (`tipo_id`),
  CONSTRAINT `fk_categoria_usuarios`
    FOREIGN KEY (`usu_id`)
    REFERENCES `finanzas`.`usuarios` (`usu_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `finanzas`.`movimiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`movimiento` (
  `mov_id` INT NOT NULL AUTO_INCREMENT,
  `mov_monto` DOUBLE NULL DEFAULT NULL,
  `mov_fecha` DATE NOT NULL,
  `mov_descripcion` VARCHAR(255) NULL DEFAULT NULL,
  `cat_id` INT NOT NULL,
  `usu_id` INT NOT NULL,
  PRIMARY KEY (`mov_id`),
  INDEX `fk_movimiento_categoria1_idx` (`cat_id` ASC) VISIBLE,
  INDEX `fk_movimiento_usuarios1_idx` (`usu_id` ASC) VISIBLE,
  CONSTRAINT `fk_movimiento_categoria1`
    FOREIGN KEY (`cat_id`)
    REFERENCES `finanzas`.`categoria` (`cat_id`),
  CONSTRAINT `fk_movimiento_usuarios1`
    FOREIGN KEY (`usu_id`)
    REFERENCES `finanzas`.`usuarios` (`usu_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `finanzas`.`aportes_meta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finanzas`.`aportes_meta` (
  `aportes_id` BIGINT NOT NULL AUTO_INCREMENT,
  `met_id` INT NOT NULL,
  `mov_id` INT NOT NULL,
  PRIMARY KEY (`aportes_id`),
  INDEX `fk_aportes_meta_metas1_idx` (`met_id` ASC) VISIBLE,
  INDEX `fk_aportes_meta_movimiento1_idx` (`mov_id` ASC) VISIBLE,
  CONSTRAINT `fk_aportes_meta_metas1`
    FOREIGN KEY (`met_id`)
    REFERENCES `finanzas`.`metas` (`met_id`),
  CONSTRAINT `fk_aportes_meta_movimiento1`
    FOREIGN KEY (`mov_id`)
    REFERENCES `finanzas`.`movimiento` (`mov_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
