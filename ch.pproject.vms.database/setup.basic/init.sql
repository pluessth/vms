DROP TABLE IF EXISTS PERSON;
CREATE TABLE PERSON (
    PERSON_NR int PRIMARY KEY NOT NULL,
    NAME varchar(100),
    FIRSTNAME varchar(100),
    SEX int,
    DATEOFBIRTH date,
    STREET varchar(100),
    ZIP varchar(100),
    CITY varchar(100),
    PHONEPRIVATE varchar(100),
    PHONEWORK varchar(100),
    PHONEMOBILE varchar(100),
    EMAIL varchar(100),
    PLAYER_LICENSE_NO varchar(100),
    REFEREE_LICENSE_NO varchar(100),
    NOTES varchar(1000)
);

CREATE UNIQUE INDEX PERSON_UIDX1 ON PERSON(PERSON_NR);

DROP TABLE IF EXISTS ROLE;
CREATE TABLE ROLE (
    ROLE_NR int PRIMARY KEY NOT NULL,
    PERSON_NR int NOT NULL,
    ROLE_UID int NOT NULL,
    FROM_DATE date,
    TO_DATE date,
    NOTES varchar(100)
);

CREATE UNIQUE INDEX ROLE_UIDX1 ON ROLE(ROLE_NR);

DROP TABLE IF EXISTS TEAM;
CREATE TABLE TEAM (
    TEAM_NR int PRIMARY KEY NOT NULL,
    NAME varchar(100),
    LICENCE_TYPE_UID int NOT NULL
);

CREATE UNIQUE INDEX TEAM_UIDX1 ON TEAM(TEAM_NR);

DROP TABLE IF EXISTS TEAM_MEMBER;
CREATE TABLE TEAM_MEMBER (
    TEAM_NR int NOT NULL,
    PERSON_NR int NOT NULL,
    SHIRT_NUMBER varchar(100)
);

CREATE UNIQUE INDEX TEAM_MEMBER_UIDX1 ON TEAM_MEMBER(TEAM_NR, PERSON_NR);

DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
    USER_NR int NOT NULL,
    USERNAME varchar(20) NOT NULL,
    PASSWORD varchar(150)
);

CREATE UNIQUE INDEX USER_UIDX1 ON USER(USER_NR);
CREATE UNIQUE INDEX USER_UIDX2 ON USER(USERNAME);

DROP TABLE IF EXISTS USER_PERMISSION;
CREATE TABLE USER_PERMISSION (
    USER_NR int NOT NULL,
    PERMISSION_NAME varchar(250) NOT NULL
);

CREATE UNIQUE INDEX USER_PERMISSION_UIDX1 ON USER_PERMISSION(USER_NR, PERMISSION_NAME);