drop table if exists users;

create table users (
    id          bigint not null auto_increment,
    email       varchar(100),
    username    varchar(50),
    password    varchar(100),
    profile_url varchar(100),
    create_at   datetime default now(),
    updated_at  datetime default now(),
    primary key (id)
);