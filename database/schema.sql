create database microblog_db;

create table roles (
	id bigint generated by default as identity primary key,
	name varchar not null
);

insert into roles(name) values ('USER'), ('MODERATOR'), ('ADMIN');

create table people (
	id bigint generated by default as identity primary key,
	username varchar(60) unique not null,
	password varchar not null,
	role_id bigint references roles(id) not null
);

create table entries (
    id bigint generated by default as identity primary key,
    text varchar(200) not null,
    image varchar(255),
    creation_date timestamp not null,
    person_id bigint references people(id) not null
);

create table tags (
    id bigint generated by default as identity primary key,
    name varchar(50) not null unique
);

create table entries_tags (
    id bigint generated by default as identity primary key,
    entry_id bigint references entries(id) not null,
    tag_id bigint references tags(id) not null
);