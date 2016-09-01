drop table student if exists;
CREATE TABLE student (
  id integer NOT NULL PRIMARY KEY,
  name varchar(10) NOT NULL,
  age integer,
  sex integer
);


INSERT INTO student(id, name, age, sex) VALUES ('2001', '小王1', '13', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2002', '小王2', '12', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2003', '大王1', '14', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2004', '大王2', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2005', '天明1', '15', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2006', '雪儿1', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2007', '天明2', '16', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2008', '雪儿2', '17', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2009', '小小冰', '14', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2010', '小豆冰', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2011', '豆豆冰', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2012', '冰豆冰', '14', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2013', '王美丽', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2014', '王丽美', '12', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2015', '阿虫1', '13', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2016', '阿三1', '11', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2017', '阿宝1', '13', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2018', '阿宝2', '13', '1');
INSERT INTO student(id, name, age, sex) VALUES ('2019', '阿宝3', '13', '0');
INSERT INTO student(id, name, age, sex) VALUES ('2020', '阿宝4', '14', '1');