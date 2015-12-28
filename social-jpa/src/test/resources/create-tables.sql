SET DATABASE SQL SYNTAX ORA TRUE;
DROP TABLE USER IF EXISTS;
DROP TABLE FRIENDS IF EXISTS;
CREATE TABLE USER (
userId INTEGER IDENTITY,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
age INTEGER NOT NULL,
login VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
createdDate DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
updatedDate DATE NOT NULL
);

CREATE TABLE FRIENDS (
friend1Id INTEGER NOT NULL,
friend2Id INTEGER NOT NULL,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
