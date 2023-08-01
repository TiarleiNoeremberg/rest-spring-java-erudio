CREATE TABLE IF NOT EXISTS person
(
    id int8 NOT NULL primary key,
	first_name varchar(80) NOT NULL,
	last_name varchar(80) NOT NULL,
	address varchar(100) NOT NULL,
	gender varchar(6) NOT NULL
);