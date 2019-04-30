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

-- Criando o usuário administrador-banco com privilegios totais para o esquema agenda
grant all on agenda.* to "administrador-banco" identified by "senha-administrador";

-- Tornando permanentes as alterações da criação de usuário.
flush privileges;
```

## 1.2 - Definir um arquivo de Conexão JDBC
Um programa java, para utilizar a interface JDBC e poder se conectar virtualmente com qualquer Banco de Dados Relacional, precisa carregar um pacote **.jar** disponibilizado para aquele modelo de SGBD específico. Portanto vamos precisar obter (fazer o download) o pacote java contendo as classes do MySQL/MariaDB, pois será esse SGBD que iremos utilizar no exemplo. vamos fazer o download deste arquivo [https://downloads.mariadb.com/Connectors/java/connector-java-2.4.1/mariadb-java-client-2.4.1.jar](https://downloads.mariadb.com/Connectors/java/connector-java-2.4.1/mariadb-java-client-2.4.1.jar).
Após salvar-lo no diretório de classes compiladas, ele será um dos pacotes **jar** que será carregado com nossa classe principal.
```java
import java.sql.Connection;
import java.sql.DriverManager;

// ConnectionFactory

public class ConexaoBanco
{
	//Nome do usuário do mysql
	private static final String NOME_DE_USUARIO = "administrador-banco";

	//Senha do mysql
	private static final String SENHA_USUARIO_BANCO = "senha-administrador";

	//Dados de caminho, porta e nome da base de dados que irá ser feita a conexão
	private static final String ENDERECO_CONEXAO_BANCO = "jdbc:mysql://localhost:3306/agenda";

	public static Connection criarConexaoBancoMySQL() throws Exception
	{
	  Class.forName("com.mysql.jdbc.Driver"); //Faz com que a classe seja carregada pela JVM 

	  //Cria a conexão com o banco de dados
	  Connection conexao_obitida = DriverManager.getConnection(ENDERECO_CONEXAO_BANCO, NOME_DE_USUARIO, SENHA_USUARIO_BANCO); 

	  return conexao_obitida;
	}
	public static void main(String[] args) throws Exception
	{
		//Recupera uma conexão com o banco de dados
		Connection conexao_caso_de_teste = criarConexaoBancoMySQL();
		//Testa se a conexão é nula
		if(conexao_caso_de_teste != null)
		{
			System.out.println("Conexão com o banco de dados obtida com sucesso!" + conexao_caso_de_teste);
			conexao_caso_de_teste.close();
		}
	}
}
```

## 1.3 - Definir uma Classe de interfaceamento com o Modelo Relacional (Model).
## 1.4 - Definir uma classe de operações Inserir, Alterar, Remover e Pesquisar (Data Acess Object).
## 1.5 - Criar uma classe de interface com o usuário.
