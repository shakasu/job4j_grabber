create table post (
id serial primary key,
name varchar(2000),
text varchar(2000),
link varchar(2000) unique,
created timestamp
);

