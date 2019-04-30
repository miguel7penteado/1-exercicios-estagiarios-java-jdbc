1-exercicios-estagiarios-java-jdbc
==================================

Exercicios para os estagiários acessando Banco de dados via JAVA usando JDBC, sem JPA

## 1.1 - Criar a tabela no SQBD (MySQL / MariaDB).
Este programa irá precisar acessar um Banco de Dados e, desta vez, escolhemos o Mysql/MariaDB como exemplo. Utilize o conteúdo do arquivo **banco.sql** para gerar um esquema e uma tabela. Você pode entrar com o código manualmente utilizando o cliente mysql:
```sql


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


```

## 1.2 - Definir um arquivo de Conexão JDBC
## 1.3 - Definir uma Classe de interfaceamento com o Modelo Relacional (Model).
## 1.4 - Definir uma classe de operações Inserir, Alterar, Remover e Pesquisar (Data Acess Object).
## 1.5 - Criar uma classe de interface com o usuário.
