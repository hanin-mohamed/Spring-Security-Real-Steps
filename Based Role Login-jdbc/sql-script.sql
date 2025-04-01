create table users
(
    username varchar(50)  not null
        primary key,
    password varchar(255) not null,
    enabled  tinyint(1)   not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint authorities_ibfk_1
        foreign key (username) references users (username)
);

create index username
    on authorities (username);

