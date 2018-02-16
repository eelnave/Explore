BEGIN
  EXECUTE IMMEDIATE 'DROP DATABASE kindness';
  EXECUTE IMMEDIATE 'DROP TABLE location';
  EXECUTE IMMEDIATE 'DROP TABLE category';
  EXECUTE IMMEDIATE 'DROP TABLE report';
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('');
END;
/

CREATE DATABASE kindness;

USE kindness;

Create table location
( location_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
, longitude decimal(9,6) NOT NULL
, latitude decimal(9,6) NOT NULL);

Create table category
(  category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
, category VARCHAR(45) NOT NULL
, description VARCHAR(45) NULL
);



CREATE TABLE report 
( report_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
, category_id 		INT 			NOT NULL
, location_id 		INT 			NOT NULL
, report_date 		TIMESTAMP(1) 	NOT NULL
, INDEX fk_Report_Category_idx (category_id ASC)
, INDEX fk_Report_Location1_idx (location_id ASC)
, CONSTRAINT fk_Report_Category
    FOREIGN KEY (category_id)
    REFERENCES Category (category_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
, CONSTRAINT fk_Report_Location1
    FOREIGN KEY (location_id)
    REFERENCES Location (location_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
