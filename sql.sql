CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS reader(
    id INT,
    name VARCHAR(10) NOT NULL,
    gender VARCHAR(2),
    birthday VARCHAR(20),
    phoneNum VARCHAR(20),
    regDate VARCHAR(20),
    borrowedNum INT,
    PRIMARY KEY(Id),
    FOREIGN KEY(Id) REFERENCES user(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS book(
    ISBN VARCHAR(20),
    name VARCHAR(50) NOT NULL,
    category VARCHAR(50),
    author VARCHAR(50),
    press VARCHAR(50),
    publicationDate VARCHAR(50),
    recordDate VARCHAR(20),
    PRIMARY KEY(ISBN)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS borrowDetail(
    id INT,
    ISBN VARCHAR(20) NOT NULL,
    borrowDate VARCHAR(20) DEFAULT NULL,
    returnDate VARCHAR(20) DEFAULT NULL,
    fine VARCHAR(20) DEFAULT NULL,
    FOREIGN KEY(Id) REFERENCES user(id),
    FOREIGN KEY(ISBN) REFERENCES book(ISBN)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `user` VALUES(1000,"root","123456");

INSERT INTO `user` VALUES(1001,"reader1","111111");

INSERT INTO `user` VALUES(1002,"reader2","222222");

INSERT INTO `user` VALUES(1003,"reader3","333333");

INSERT INTO
    reader
VALUES(
        1000,
        "root",
        "m",
        "2002-01-01",
        "12312345678",
        "2020-05-01",
        5
    );

INSERT INTO
    reader
VALUES(
        1001,
        "reader1",
        "w",
        "2002-02-05",
        "15012345678",
        "2020-06-02",
        5
    );

INSERT INTO
    reader
VALUES(
        1002,
        "reader2",
        "m",
        "2002-05-01",
        "15612345678",
        "2021-05-01",
        5
    );

INSERT INTO
    reader
VALUES(
        1003,
        "reader3",
        "w",
        "2002-06-01",
        "18612345678",
        "2022-06-01",
        5
    );

insert into
    book
values(
        "9787010191652",
        "资本论",
        "社会学",
        "卡尔·马克思",
        "人民教育出版社",
        "2018-03-01",
        "2022-05-26"
    );

insert into
    book
values(
        "9787040396614",
        "线性代数",
        "教辅",
        "同济大学",
        "高等教育出版社",
        "2020-05",
        "2022-05-26"
    );

insert into
    book
values(
        "9787040396638 ",
        "高等数学",
        "教辅",
        "同济大学",
        "高等教育出版社",
        "2020-05",
        "2022-05-26"
    );

insert into
    book
values(
        "9787100164771",
        "经济学基础",
        "经济学",
        "马歇尔",
        "商务印书馆",
        "2020-05-01",
        "2022-05-27"
    );

insert into
    book
values(
        "9787302503880",
        "python",
        "教辅",
        "清华大学",
        "清华大学出版社",
        "2018-10-01",
        "2022-05-25"
    );

insert into
    book
values(
        "9787302581260",
        "Java",
        "教辅",
        "清华大学",
        "清华大学出版社",
        "2021-07-01",
        "2022-05-25"
    );
