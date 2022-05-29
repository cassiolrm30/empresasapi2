create database empresasapi2;
use empresasapi2;

create table cliente(
	idcliente		integer			auto_increment,
    nome			varchar(150)	not null,
    senha			varchar(15)		not null unique,
    email			varchar(100)	not null unique,
    primary key(idcliente));
   
show tables;