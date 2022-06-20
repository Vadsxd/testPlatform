alter table if exists user_role drop constraint if exists user_fk;
alter table if exists user_role drop constraint if exists role_fk;

drop table if exists users cascade;
drop table if exists "role" cascade;
drop table if exists user_role cascade;

create table users
(
    id       int8 generated by default as identity,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);

create table role
(
    id     int8 generated by default as identity,
    "name" varchar(255) not null,
    primary key (id)
);

create table user_role
(
    user_id int8 not null,
    role_id int8 not null,
    primary key (user_id, role_id)
);

alter table if exists user_role add constraint user_fk foreign key (user_id) references users;
alter table if exists user_role add constraint role_fk foreign key (role_id) references role;

insert into users (username, password)
values ('Admin', '228');

insert into role ("name")
values ('Admin');

insert into user_role (user_id, role_id)
values (1, 1);