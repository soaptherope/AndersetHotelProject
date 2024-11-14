-- liquibase formatted sql

-- alisher s: create the table to store serialized data for Hotel and Apartment

create table serialized_data (
    id serial primary key,
    type varchar(255) not null,
    data bigint not null
);


