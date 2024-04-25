
use PlayStore;
create table owner(id INT AUTO_INCREMENT PRIMARY KEY, username varchar(20),password varchar(20));
-- Registering a new user (inserting hashed password)
INSERT INTO owner  VALUES (1,'Shrashti', 'PurohitShrashti@123');
INSERT INTO owner  VALUES (2,'Saurabh', 'Saurabh@12345');

ALTER TABLE owner CHANGE COLUMN name username VARCHAR(255);
ALTER TABLE owner CHANGE COLUMN password password VARCHAR(255);



select * from owner;
