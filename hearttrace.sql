SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_database = utf8;
SET character_set_results = utf8;
SET character_set_server = utf8;
DROP DATABASE HeartTrace;
CREATE DATABASE HeartTrace;

USE HeartTrace;

CREATE TABLE UserList(
    username CHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    modified BIGINT NOT NULL,
    nickname VARCHAR(30),
    gender CHAR(10),
    birthday VARCHAR (30),
    email VARCHAR (30),
    school VARCHAR (30),
    signature VARCHAR (300),
    headimage LONGTEXT
);

CREATE TABLE Diary(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diarybook BIGINT,
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    textSize FLOAT,
    textAlignment INT,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diaryNo ON Diary(username, id);

CREATE TABLE Diarybook(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diarybookName VARCHAR(30),
    description VARCHAR(1000),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diarybookNo ON Diarybook(username, id);

CREATE TABLE DiaryLabel(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diary BIGINT NOT NULL,
    label BIGINT NOT NULL,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diaryLabelNO ON DiaryLabel(username, diary, label);

CREATE TABLE Label(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    labelname VARCHAR(30),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX labelNo on Label(username, id);

CREATE TABLE Sentence(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentencebook BiGINT,
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    textSize FLOAT,
    textAlignment INT,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentenceNo ON Sentence(username, id);

CREATE TABLE Sentencebook(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentencebookName VARCHAR(30),
    description VARCHAR(1000),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentencebookNo ON Sentencebook(username, id);

CREATE TABLE SentenceLabel(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentence BIGINT NOT NULL,
    label BIGINT NOT NULL,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentenceLabelNO ON SentenceLabel(username, sentence, label);






CREATE TABLE Diary_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diarybook BIGINT,
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    textSize FLOAT,
    textAlignment INT,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diary_deleteNo ON Diary_delete(username, id);

CREATE TABLE Diarybook_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diarybookName VARCHAR(30),
    description VARCHAR(1000),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diarybook_deleteNo ON Diarybook_delete(username, id);

CREATE TABLE DiaryLabel_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    diary BIGINT NOT NULL,
    label BIGINT NOT NULL,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX diaryLabel_deleteNO ON DiaryLabel_delete(username, diary, label);

CREATE TABLE Label_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    labelname VARCHAR(30),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX label_deleteNo on Label_delete(username, id);

CREATE TABLE Sentence_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentencebook BIGINT,
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    textSize FLOAT,
    textAlignment INT,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentence_deleteNo ON Sentence_delete(username, id);

CREATE TABLE Sentencebook_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentencebookName VARCHAR(30),
    description VARCHAR(1000),
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentencebook_deleteNo ON Sentencebook_delete(username, id);

CREATE TABLE SentenceLabel_delete(
    username CHAR(30) NOT NULL,
    id BIGINT NOT NULL,
    modified BIGINT NOT NULL,
    sentence BIGINT NOT NULL,
    label BIGINT NOT NULL,
    anchor BIGINT NOT NULL
);
CREATE UNIQUE INDEX sentenceLabel_deleteNO ON SentenceLabel_delete(username, sentence, label);



