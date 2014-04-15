DROP DATABASE IF EXISTS NUR;
CREATE DATABASE NUR;
USE NUR;
DROP TABLE IF EXISTS HP_USER;
CREATE TABLE HP_USER (
  Gid                 VARCHAR(36) NOT NULL,
  WatsonInstance      VARCHAR(10),
  DomainUserName      VARCHAR(30),
  DomainUserNameIndex VARCHAR(30),
  Name                VARCHAR(30),
  FirstName           VARCHAR(30),
  LastName            VARCHAR(30),
  EmployeeID          VARCHAR(10),
  Email               VARCHAR(30),
  UserStatus          VARCHAR(1),
  LastLoginDate       DATE,
  CreateDate          DATE,
  CreatedBy           VARCHAR(32),
  LastModifyDate      DATE,
  LastModifiedBy      VARCHAR(32),
  PRIMARY KEY (Gid)
)
  DEFAULT CHARSET =UTF8;

DROP TABLE IF EXISTS REQUEST;
CREATE TABLE REQUEST (
  RequestId                 VARCHAR(36) NOT NULL,
  WatsonInstance            VARCHAR(10),
  DomainUserName            VARCHAR(30),
  Country                   VARCHAR(10),
  EmployeeID                VARCHAR(10),
  Email                     VARCHAR(30),
  RequestDate               DATE,
  Comments                  VARCHAR(100),
  ManagerDomainUserName     VARCHAR(30),
  ManagerEmployeeId         VARCHAR(10),
  ManagerEmail              VARCHAR(30),
  ManagerProceed            VARCHAR(5),
  ManagerProceedDate        DATE,
  ManagerComments           VARCHAR(100),
  CountryRepDomainUserName  VARCHAR(30),
  CountryRepEmployeeId      VARCHAR(10),
  CountryRepEmail           VARCHAR(30),
  CountryRepProceed         VARCHAR(5),
  CountryRepProceedDate     DATE,
  CountryRepComments        VARCHAR(100),
  RegionalRepDomainUserName VARCHAR(30),
  RegionalRepEmployeeId     VARCHAR(10),
  RegionalRepEmail          VARCHAR(30),
  RegionalRepProceed        VARCHAR(5),
  RegionalRepProceedDate    DATE,
  RegionalRepComments       VARCHAR(100),
  Status                    VARCHAR(4),
  PRIMARY KEY (RequestId)
)
  DEFAULT CHARSET =UTF8;

DROP TABLE IF EXISTS COUNTRY_REP;
CREATE TABLE COUNTRY_REP (
  Gid             VARCHAR(36) NOT NULL PRIMARY KEY,
  WatsonInstance  VARCHAR(10),
  DomainUserName  VARCHAR(30),
  EmployeeID      VARCHAR(10),
  Email           VARCHAR(30),
  CountryCode     VARCHAR(3),
  CountryName     VARCHAR(30),
  EffectiveDate   DATE,
  TerminateDate   DATE,
  EffectiveStatus VARCHAR(1),
  AssignedBy      VARCHAR(36),
  PRIMARY KEY (Gid)
)
  DEFAULT CHARSET =UTF8;

DROP TABLE IF EXISTS REGIONAL_REP;
CREATE TABLE REGIONAL_REP (
  Gid             VARCHAR(36) NOT NULL PRIMARY KEY,
  WatsonInstance  VARCHAR(10),
  DomainUserName  VARCHAR(30),
  EmployeeID      VARCHAR(10),
  Email           VARCHAR(30),
  Region          VARCHAR(10),
  EffectiveDate   DATE,
  TerminateDate   DATE,
  EffectiveStatus VARCHAR(1),
  AssignedBy      VARCHAR(36),
  PRIMARY KEY (Gid)
)
  DEFAULT CHARSET =UTF8;