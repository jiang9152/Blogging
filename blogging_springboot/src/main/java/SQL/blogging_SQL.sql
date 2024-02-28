CREATE DATABASE IF NOT EXISTS project1;
USE project1;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS article;

CREATE TABLE users (
    id	          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	username	  VARCHAR(30) NOT NULL UNIQUE,
	`password`	  VARCHAR(100),
    nickname	  VARCHAR(30),
    email		  VARCHAR(50),
    create_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 10;

CREATE TABLE category (
    id	          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	category_name  VARCHAR(30) NOT NULL,
	category_alias VARCHAR(30) NOT NULL,
    create_user	  INT NOT NULL,
    create_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT category_user_FK FOREIGN KEY (create_user) REFERENCES users (id)
) ;

CREATE TABLE article (
    id	          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	title         VARCHAR(30) NOT NULL,
	content       VARCHAR(1000) NOT NULL,
    state         CHAR(10) NOT NULL,
    category_id   INT NOT NULL,
    create_user	  INT NOT NULL,
    create_time   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT article_category_FK FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT article_user_FK FOREIGN KEY (create_user) REFERENCES users (id)
) ;

