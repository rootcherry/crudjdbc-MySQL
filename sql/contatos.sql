create database agenda

default character set utf8

default collate utf8_general_ci;

use agenda;

create table if not exists contato (
codigo int(11) not null auto_increment,
nome varchar (45) not null,
email varchar (45) not null, -- unique
dataCadastro date, 
primary key (codigo)
) default charset utf8;
