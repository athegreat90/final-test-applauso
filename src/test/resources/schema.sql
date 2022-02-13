create schema IF NOT EXISTS ACCION_FINALTEST;

create table IF NOT EXISTS accion_finaltest.employee
(
    id            int          not null
        primary key,
    birthday      datetime     not null,
    document      varchar(50)  not null,
    lastname      varchar(100) not null,
    name_employee varchar(100) not null,
    role_employee varchar(50)  not null,
    salary        double       not null,
    start_date    datetime     not null,
    type_document varchar(2)   not null
);

create table IF NOT EXISTS accion_finaltest.hibernate_sequence
(
    next_val bigint null
);

create table IF NOT EXISTS accion_finaltest.movie
(
    id           int           not null
        primary key,
    availability bit           null,
    description  varchar(2000) null,
    rental_price double        not null,
    sale_price   double        not null,
    stock        int           not null,
    title        varchar(200)  not null
);

create table IF NOT EXISTS accion_finaltest.movie_picture
(
    id  int          not null
        primary key,
    url varchar(300) not null
);

create table IF NOT EXISTS accion_finaltest.movie_x_pictures
(
    id         int not null
        primary key,
    id_movie   int null,
    id_picture int not null,
    constraint FK3l0acp89ygaw72gbcqs22b9ya
        foreign key (id_movie) references accion_finaltest.movie (id),
    constraint FKhxal4x7s7g7mndocmrguhbvts
        foreign key (id_picture) references accion_finaltest.movie_picture (id)
);

create table IF NOT EXISTS accion_finaltest.role
(
    id          int          not null
        primary key,
    description varchar(200) null,
    name        varchar(50)  not null
);

create table IF NOT EXISTS accion_finaltest.user_system
(
    username varchar(50)  not null
        primary key,
    email    varchar(200) not null,
    name     varchar(300) not null,
    password varchar(200) not null
);

create table IF NOT EXISTS accion_finaltest.user_movie_like
(
    id       int         not null
        primary key,
    id_movie int         not null,
    username varchar(50) not null,
    constraint FKjnwbpu11edle3hmpxlq9akk9m
        foreign key (username) references accion_finaltest.user_system (username),
    constraint FKpxh3tk1fcw1m0vr2ok9vfj3ey
        foreign key (id_movie) references accion_finaltest.movie (id)
);

create table IF NOT EXISTS accion_finaltest.user_order
(
    id       int         not null
        primary key,
    count    double      null,
    id_movie int         not null,
    username varchar(50) not null,
    constraint FK9wenhrf8w1mm0fpu648pilufe
        foreign key (id_movie) references accion_finaltest.movie (id),
    constraint FKtm3jk82j0aqobep2t6swea6dw
        foreign key (username) references accion_finaltest.user_system (username)
);

create table IF NOT EXISTS accion_finaltest.user_rent
(
    id         int         not null
        primary key,
    date_begin datetime    not null,
    date_end   datetime    not null,
    id_movie   int         null,
    username   varchar(50) not null,
    constraint FKi218hixextpyjcojjq6ajm6nu
        foreign key (username) references accion_finaltest.user_system (username),
    constraint FKo149jyxe6qk05u4kmtv2x2uss
        foreign key (id_movie) references accion_finaltest.movie (id)
);

create table IF NOT EXISTS accion_finaltest.user_roles
(
    user_id varchar(50) not null,
    role_id int         not null,
    primary key (user_id, role_id),
    constraint FKq39iguv9p8up12cxmuup4sdvm
        foreign key (user_id) references accion_finaltest.user_system (username),
    constraint FKrhfovtciq1l558cw6udg0h0d3
        foreign key (role_id) references accion_finaltest.role (id)
);
