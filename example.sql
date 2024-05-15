
create database mvcexample;
use mvcexample;
create table users (
	id int(3) not null auto_increment,
    name varchar(120) not null,
    email varchar(220) not null,
    country varchar(120),
    primary key(id)
)