SET DATABASE SQL SYNTAX ORA TRUE;
DROP TABLE USER IF EXISTS;
DROP TABLE FRIENDS IF EXISTS;
DROP TABLE IMAGE IF EXISTS;

CREATE TABLE USER (
userId INTEGER IDENTITY,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
age INTEGER NOT NULL,
login VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
updatedDate TIMESTAMP NOT NULL
);

CREATE TABLE FRIENDS (
friend1Id INTEGER NOT NULL,
friend2Id INTEGER NOT NULL,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IMAGE (
--userId INTEGER NOT NULL,
imageId INTEGER NOT NULL IDENTITY,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
url VARCHAR(255) NOT NULL
);

CREATE TABLE USER_IMAGE (
images_imageId INTEGER NOT NULL,
User_userId INTEGER NOT NULL
);