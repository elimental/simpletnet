-- account

CREATE TABLE account (
  id INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(45) NULL,
  lastName VARCHAR(45) NULL,
  patronymicName VARCHAR(45) NULL,
  birthDay DATE NULL,
  email VARCHAR(45) NULL,
  icq VARCHAR(45) NULL,
  skype VARCHAR(45) NULL,
  additionalInfo VARCHAR(1000) NULL,
  password VARCHAR(200) NULL,
  regDate DATE NULL,
  photo LONGBLOB NULL,
  userRole INT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC))
ENGINE = InnoDB;

-- account_groups

CREATE TABLE account_groups (
  userId INT NULL,
  groupId INT NULL,
  userRole INT NULL,
  status INT NULL)
ENGINE = InnoDB;

-- groups

CREATE TABLE groups (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NULL,
  owner INT NULL,
  picture LONGBLOB NULL,
  createDate DATE NULL,
  description VARCHAR(1000) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX groupId_UNIQUE (id ASC))
ENGINE = InnoDB;

-- message

CREATE TABLE message (
  id INT NOT NULL AUTO_INCREMENT,
  text VARCHAR(10000) NULL,
  createDate DATETIME NULL,
  author INT NULL,
  type INT NULL,
  destination INT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;

-- phone

CREATE TABLE phone (
  number VARCHAR(45) NULL,
  type INT NULL,
  owner INT NULL)
ENGINE = InnoDB;

-- relationship

CREATE TABLE IF NOT EXISTS relationship (
  userOneId INT NULL,
  userTwoId INT NULL,
  status INT NULL,
  lastActionUser INT NULL)
ENGINE = InnoDB