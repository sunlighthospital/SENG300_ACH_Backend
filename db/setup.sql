--
-- Interfacing User addition
--
DROP USER IF EXISTS 'client'@'localhost';
CREATE USER 'client'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON SENG300_Hospital.* TO 'client'@'localhost';
FLUSH PRIVILEGES;
