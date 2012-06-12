SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `telpro` DEFAULT CHARACTER SET latin1 COLLATE latin1_german1_ci ;
USE `telpro` ;

-- -----------------------------------------------------
-- Table `telpro`.`Sensor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Sensor` (
  `idSensor` VARCHAR(16) NOT NULL ,
  `IP` INT NULL ,
  `Location` VARCHAR(45) NULL ,
  PRIMARY KEY (`idSensor`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`PropertyType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`PropertyType` (
  `idPropertyType` INT NOT NULL ,
  `Name` VARCHAR(45) NULL ,
  PRIMARY KEY (`idPropertyType`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`EventType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`EventType` (
  `idEventType` INT NOT NULL ,
  `Name` VARCHAR(45) NULL ,
  `Description` TEXT NULL ,
  PRIMARY KEY (`idEventType`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Trigger`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Trigger` (
  `idTrigger` INT NOT NULL ,
  `PropertyType_idPropertyType` INT NOT NULL ,
  `EventType_idEventType` INT NOT NULL ,
  PRIMARY KEY (`idTrigger`) ,
  INDEX `fk_Trigger_PropertyType1` (`PropertyType_idPropertyType` ASC) ,
  INDEX `fk_Trigger_EventType1` (`EventType_idEventType` ASC) ,
  CONSTRAINT `fk_Trigger_PropertyType1`
    FOREIGN KEY (`PropertyType_idPropertyType` )
    REFERENCES `telpro`.`PropertyType` (`idPropertyType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Trigger_EventType1`
    FOREIGN KEY (`EventType_idEventType` )
    REFERENCES `telpro`.`EventType` (`idEventType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Event`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Event` (
  `idEvent` INT NOT NULL ,
  `EventType_idEventType` INT NOT NULL ,
  `Timestamp` TIMESTAMP NULL ,
  `Sensor_idSensor` VARCHAR(16) NULL ,
  `Trigger_idTrigger` INT NULL ,
  PRIMARY KEY (`idEvent`, `EventType_idEventType`) ,
  INDEX `fk_Event_Sensor1` (`Sensor_idSensor` ASC) ,
  INDEX `fk_Event_Trigger1` (`Trigger_idTrigger` ASC) ,
  INDEX `fk_Event_EventType1` (`EventType_idEventType` ASC) ,
  CONSTRAINT `fk_Event_Sensor1`
    FOREIGN KEY (`Sensor_idSensor` )
    REFERENCES `telpro`.`Sensor` (`idSensor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_Trigger1`
    FOREIGN KEY (`Trigger_idTrigger` )
    REFERENCES `telpro`.`Trigger` (`idTrigger` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_EventType1`
    FOREIGN KEY (`EventType_idEventType` )
    REFERENCES `telpro`.`EventType` (`idEventType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Property`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Property` (
  `idProperty` INT NOT NULL ,
  `PropertyType_idPropertyType` INT NOT NULL ,
  `Sensor_idSensor` VARCHAR(16) NOT NULL ,
  `Value` DOUBLE NULL ,
  `Timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`idProperty`, `PropertyType_idPropertyType`, `Sensor_idSensor`) ,
  INDEX `fk_Property_PropertyType1` (`PropertyType_idPropertyType` ASC) ,
  INDEX `fk_Property_Sensor1` (`Sensor_idSensor` ASC) ,
  CONSTRAINT `fk_Property_PropertyType1`
    FOREIGN KEY (`PropertyType_idPropertyType` )
    REFERENCES `telpro`.`PropertyType` (`idPropertyType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Property_Sensor1`
    FOREIGN KEY (`Sensor_idSensor` )
    REFERENCES `telpro`.`Sensor` (`idSensor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Action`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Action` (
  `idAction` INT NOT NULL ,
  `Name` VARCHAR(45) NULL ,
  `Description` TEXT NULL ,
  PRIMARY KEY (`idAction`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Sensor_has_Trigger`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Sensor_has_Trigger` (
  `Sensor_idSensor` VARCHAR(16) NOT NULL ,
  `Trigger_idTrigger` INT NOT NULL ,
  PRIMARY KEY (`Sensor_idSensor`, `Trigger_idTrigger`) ,
  INDEX `fk_Sensor_has_Trigger_Trigger1` (`Trigger_idTrigger` ASC) ,
  INDEX `fk_Sensor_has_Trigger_Sensor1` (`Sensor_idSensor` ASC) ,
  CONSTRAINT `fk_Sensor_has_Trigger_Sensor1`
    FOREIGN KEY (`Sensor_idSensor` )
    REFERENCES `telpro`.`Sensor` (`idSensor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sensor_has_Trigger_Trigger1`
    FOREIGN KEY (`Trigger_idTrigger` )
    REFERENCES `telpro`.`Trigger` (`idTrigger` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Sensor_has_PropertyType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Sensor_has_PropertyType` (
  `Sensor_idSensor` VARCHAR(16) NOT NULL ,
  `PropertyType_idPropertyType` INT NOT NULL ,
  PRIMARY KEY (`Sensor_idSensor`, `PropertyType_idPropertyType`) ,
  INDEX `fk_Sensor_has_PropertyType_PropertyType1` (`PropertyType_idPropertyType` ASC) ,
  INDEX `fk_Sensor_has_PropertyType_Sensor1` (`Sensor_idSensor` ASC) ,
  CONSTRAINT `fk_Sensor_has_PropertyType_Sensor1`
    FOREIGN KEY (`Sensor_idSensor` )
    REFERENCES `telpro`.`Sensor` (`idSensor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sensor_has_PropertyType_PropertyType1`
    FOREIGN KEY (`PropertyType_idPropertyType` )
    REFERENCES `telpro`.`PropertyType` (`idPropertyType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`Devices`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`Devices` (
  `idDevices` INT NOT NULL ,
  PRIMARY KEY (`idDevices`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telpro`.`EventType_has_Action`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `telpro`.`EventType_has_Action` (
  `EventType_idEventType` INT NOT NULL ,
  `Action_idAction` INT NOT NULL ,
  PRIMARY KEY (`EventType_idEventType`, `Action_idAction`) ,
  INDEX `fk_EventType_has_Action_Action1` (`Action_idAction` ASC) ,
  INDEX `fk_EventType_has_Action_EventType1` (`EventType_idEventType` ASC) ,
  CONSTRAINT `fk_EventType_has_Action_EventType1`
    FOREIGN KEY (`EventType_idEventType` )
    REFERENCES `telpro`.`EventType` (`idEventType` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EventType_has_Action_Action1`
    FOREIGN KEY (`Action_idAction` )
    REFERENCES `telpro`.`Action` (`idAction` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
