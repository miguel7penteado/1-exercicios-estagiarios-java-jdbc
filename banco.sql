


-- Criando um esquema chamado agenda;
create database agenda;

-- Criando uma tabela de exemplo chamada contatos;
create table contatos
(
	id integer not null auto_increment,
	nome varchar(40),
	idade integer,
	dataCadastro date
);

-- Criando uma chave primária para esta tabela;
alter table agenda.contatos add contraint "chave1" primary key(id);

-- Criando o usuário administrador-banco com privilegios totais para o esquema agenda
grant all on agenda.* to "administrador-banco" identified by "senha-administrador";

-- Tornando permanentes as alterações da criação de usuário.
flush privileges;

