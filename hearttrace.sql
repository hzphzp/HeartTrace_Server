CREATE DATABASE HeartTrace;

USE HeartTrace;

CREATE TABLE UserList(
    username CHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    age INT,
    gender BOOL,
    school VARCHAR(30) ,
    address VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE Diary(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybook INT, 
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    texSize FLOAT,
    textAlignment INT
);
CREATE UNIQUE INDEX diaryNo ON Diary(username, id);

CREATE TABLE Diarybook(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybookName VARCHAR(30),
    description VARCHAR(1000)
);
CREATE UNIQUE INDEX diarybookNo ON Diarybook(username, id);

CREATE TABLE DiaryLabel(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diary INT NOT NULL,
    label INT NOT NULL
);
CREATE UNIQUE INDEX diaryLabelNO ON DiaryLabel(username, diary, label);

CREATE TABLE Label(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,   
    labelname VARCHAR(30)
);
CREATE UNIQUE INDEX labelNo on Label(username, id);

CREATE TABLE Sentence(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybook INT, 
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    texSize FLOAT,
    textAlignment INT
);
CREATE UNIQUE INDEX sentenceNo ON Sentence(username, id);

CREATE TABLE Sentencebook(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    setencebookName VARCHAR(30),
    description VARCHAR(1000)
);
CREATE UNIQUE INDEX sentencebookNo ON Sentencebook(username, id);

CREATE TABLE SentenceLabel(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    sentence INT NOT NULL,
    label INT NOT NULL
);
CREATE UNIQUE INDEX sentenceLabelNO ON SentenceLabel(username, sentence, label);



CREATE TABLE Diary_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybook INT, 
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    texSize FLOAT,
    textAlignment INT
);
CREATE UNIQUE INDEX diary_deleteNo ON Diary_delete(username, id);

CREATE TABLE Diarybook_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybookName VARCHAR(30),
    description VARCHAR(1000)
);
CREATE UNIQUE INDEX diarybook_deleteNo ON Diarybook_delete(username, id);

CREATE TABLE DiaryLabel_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diary INT NOT NULL,
    label INT NOT NULL
);
CREATE UNIQUE INDEX diaryLabel_deleteNO ON DiaryLabel_delete(username, diary, label);

CREATE TABLE Label_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,   
    labelname VARCHAR(30)
);
CREATE UNIQUE INDEX label_deleteNo on Label_delete(username, id);

CREATE TABLE Sentence_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    diarybook INT, 
    htmlText VARCHAR(8000),
    text VARCHAR(8000),
    date BIGINT,
    islike BOOL,
    letterSpacing FLOAT,
    lineSpacingMultiplier INT,
    lineSpacingExtra INT,
    texSize FLOAT,
    textAlignment INT
);
CREATE UNIQUE INDEX sentence_deleteNo ON Sentence_delete(username, id);

CREATE TABLE Sentencebook_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    setencebookName VARCHAR(30),
    description VARCHAR(1000)
);
CREATE UNIQUE INDEX sentencebook_deleteNo ON Sentencebook_delete(username, id);

CREATE TABLE SentenceLabel_delete(
    anchor BIGINT NOT NULL,
    username CHAR(30) NOT NULL,
    id INT NOT NULL,
    sentence INT NOT NULL,
    label INT NOT NULL
);
CREATE UNIQUE INDEX sentenceLabel_deleteNO ON SentenceLabel_delete(username, sentence, label);
