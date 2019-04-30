
create database agenda;

create table contatos
(
	id integer not null auto_increment,
	nome varchar(40),
	idade integer,
	dataCadastro date
);

alter table agenda.contatos add contraint "chave1" primary key(id);
