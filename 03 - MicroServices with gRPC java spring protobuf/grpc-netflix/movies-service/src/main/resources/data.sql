DROP TABLE IF EXISTS movies;
CREATE TABLE movies as SELECT * FROM CSVREAD('classpath:movies.csv');