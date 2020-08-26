\c schema

create table post (
id serial primary key,
name varchar(2000),
text varchar(2000),
link varchar(2000),
created timestamp
);
