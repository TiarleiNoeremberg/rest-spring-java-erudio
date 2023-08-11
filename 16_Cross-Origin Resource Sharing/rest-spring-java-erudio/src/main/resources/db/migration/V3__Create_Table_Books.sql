CREATE TABLE IF NOT EXISTS books
(
	id bigserial primary key,
	author varchar(80) NOT NULL,
	launch_date date NOT NULL,
	price decimal(65,2) NOT NULL,
	title varchar(120) NOT NULL
)