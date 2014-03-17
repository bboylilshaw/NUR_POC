USE NUR;
DROP TABLE IF EXISTS HPUSER;
CREATE TABLE IF NOT EXISTS HPUSER (
  Gid            VARCHAR(36) NOT NULL PRIMARY KEY,
  DomainUserName VARCHAR(30) NOT NULL,
  Name           VARCHAR(30),
  FirstName      VARCHAR(30),
  LastName       VARCHAR(30),
  EmployeeID     INT(10),
  EmployeeEmail  VARCHAR(30),
  UserStatus     VARCHAR(10),
  LastLoginDate  DATETIME,
  CreateDate     DATETIME,
  LastModifyDate DATETIME,
  CreatedBy      VARCHAR(32),
  LastModifiedBy VARCHAR(32)
);

DROP TABLE IF EXISTS USER_REG_REQUEST;
CREATE TABLE IF NOT EXISTS USER_REG_REQUEST (
  RequestID              VARCHAR(36) NOT NULL PRIMARY KEY,
  DomainUserName         VARCHAR(30) NOT NULL,
  Instance               VARCHAR(10),
  EmployeeID             INT(10),
  EmployeeEmail          VARCHAR(30),
  RequestDate            DATETIME,
  Comments               VARCHAR(100),
  ManagerEmail           VARCHAR(30),
  ManagerProceed         VARCHAR(5),
  ManagerProceedDate     DATETIME,
  ManagerComments        VARCHAR(100),
  CountryRepEmail        VARCHAR(30),
  CountryRepProceed      VARCHAR(5),
  CountryRepProceedDate  DATETIME,
  CountryRepComments     VARCHAR(100),
  RegionalRepEmail       VARCHAR(30),
  RegionalRepProceed     VARCHAR(5),
  RegionalRepProceedDate DATETIME,
  RegionalRepComments    VARCHAR(100)
)