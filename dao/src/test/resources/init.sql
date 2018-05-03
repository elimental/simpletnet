CREATE TABLE account (
  userId INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(45) NOT NULL,
  lastName VARCHAR(45) NOT NULL,
  patronymicName VARCHAR(45) NOT NULL,
  birthDay DATE NOT NULL,
  homeAddress VARCHAR(45) NULL,
  workAddress VARCHAR(45) NULL,
  email VARCHAR(45) NOT NULL,
  icq VARCHAR(45) NULL,
  skype VARCHAR(45) NULL,
  additionalInfo VARCHAR(1000) NULL,
  PRIMARY KEY (userId),
  UNIQUE INDEX id_UNIQUE (userId ASC));

INSERT INTO account (firstName, lastName, patronymicName, birthDay, homeAddress, workAddress, email, icq,
  skype, additionalInfo)
  VALUES ('Dima', 'Andreev', 'Borisovich', '1978-02-13', 'Spb', 'Spb', 'elimental@bk.ru', '49224940', 'elimetal13',
    'adfasdfasdf'),
         ('Vasya','Petrov', 'Ivanovich', '1981-12-17', 'Msk', 'Msk', 'vasya@bk.ru', '49444940', 'vasya17',
    'adfasdfasdf'),
         ('Petya','Ivanov', 'Petrovich', '1970-06-21', NULL, NULL, 'petya@bk.ru', '43444940', 'petya21',
    'adfasdfasdf');

CREATE TABLE groupp (
  groupId INT NOT NULL AUTO_INCREMENT,
  groupName VARCHAR(45) NOT NULL,
  groupOwner INT NOT NULL,
  PRIMARY KEY (groupId),
  UNIQUE INDEX groupId_UNIQUE (groupId ASC));
  
INSERT INTO groupp (groupName, groupOwner)
  VALUES ('group1',1),('group2',2),('group3',3)
