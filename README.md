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
arquivo **ConexaoBanco.java**
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
Vamos agora criar um continente para os registros em memória digitados pelo usuário em seu programa java de interface com o Banco.
arquivo **MapeiaTabelaContato.java**
```java
import java.util.Date;

// Contato
 
public class MapeiaTabelaContato
{

	private int     cpf;
	private String  nome;
	private int     idade;
	private Date    dataCadastro;

	// getId
	public int obtemCpf()
	{
		return cpf;
	}

	// setId
	public void gravaCpf(int parametro_cpf)
	{
		this.cpf = parametro_cpf;
	}

	// getNome
	public String obtemNome()
	{
		return nome;
	}

	public void gravaNome(String parametro_nome)
	{
		this.nome = parametro_nome;
	}

	// getIdade
	public int obtemIdade()
	{
		return idade;
	}

	// setIdade
	public void gravaIdade(int parametro_idade)
	{
		this.idade = parametro_idade;
	}

	// getDataCadastro
	public Date obtemDataCadastro()
	{
		return dataCadastro;
	}

	// setDataCadastro
	public void gravaDataCadastro(Date paremtro_data_cadastro)
	{
		this.dataCadastro = paremtro_data_cadastro;
	}
}

```

## 1.4 - Definir uma classe de operações Inserir, Alterar, Remover e Pesquisar (Data Acess Object).
Agora vamos criar uma classe que utiliza as classes anteriores para realizar as operações `inserir`, `alterar`, `pesquisar` e `remover`. 
arquivo **GerenciaOperacoesCadastro.java**
```java 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import br.com.agenda.factory.ConnectionFactory;
import br.com.agenda.model.MapeiaTabelaContato;

// ContatoDAO
// CRUD
 
public class GerenciaOperacoesCadastro
{
	// save
	public void inserir(MapeiaTabelaContato registro_em_memoria)
	{

		/*
		* Isso é uma sql comum, os ? são os parâmetros que nós vamos adicionar
		* na base de dados
		*/

		String sql_a_ser_enviado_ao_banco               = "INSERT INTO contatos(nome,idade,dataCadastro)" + " VALUES(?,?,?)";
		Connection conexao_atual_com_banco              = null;
		PreparedStatement conteudos_e_comandos_ao_banco = null;

		try
		{
			//Cria uma conexão com o banco
			conexao_atual_com_banco = ConnectionFactory.criarConexaoBancoMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			conteudos_e_comandos_ao_banco = conexao_atual_com_banco.prepareStatement(sql_a_ser_enviado_ao_banco);

			//Adiciona o valor do primeiro parâmetro da sql
			conteudos_e_comandos_ao_banco.setString(1, registro_em_memoria.obtemNome());
			
			//Adicionar o valor do segundo parâmetro da sql
			conteudos_e_comandos_ao_banco.setInt(2, registro_em_memoria.obtemIdade());
			
			//Adiciona o valor do terceiro parâmetro da sql
			conteudos_e_comandos_ao_banco.setDate(3, new Date(registro_em_memoria.obtemDataCadastro().getTime()));

			//Executa a sql para inserção dos dados
			conteudos_e_comandos_ao_banco.execute();

		}
		catch(Exception exite_algum_erro_relatado_na_operacao_execucao)
		{
			exite_algum_erro_relatado_na_operacao_execucao.printStackTrace();
		}
		finally
		{
			//Fecha as conexões
			try
			{
				if(conteudos_e_comandos_ao_banco != null)
				{
					conteudos_e_comandos_ao_banco.close();
				}

				if(conexao_atual_com_banco != null)
				{
					conexao_atual_com_banco.close();
				}
			}
			catch(Exception exite_algum_erro_relatado_na_operacao_fechamento)
			{
				exite_algum_erro_relatado_na_operacao_fechamento.printStackTrace();
			}
		}
	 }
 
	public void removePorCpf(int id)
	{
		String sql = "DELETE FROM contatos WHERE id = ?";

		Connection conexao_atual_com_banco = null;
		PreparedStatement conteudos_e_comandos_ao_banco = null;

		try
		{
			conexao_atual_com_banco = ConnectionFactory.criarConexaoBancoMySQL();
			conteudos_e_comandos_ao_banco = conexao_atual_com_banco.prepareStatement(sql);
			conteudos_e_comandos_ao_banco.setInt(1, id);
			conteudos_e_comandos_ao_banco.execute();
		}
		catch (Exception exite_algum_erro_relatado_na_operacao_execucao)
		{
			// TODO Auto-generated catch block
			exite_algum_erro_relatado_na_operacao_execucao.printStackTrace();
		}
		finally
		{
			try
			{
				if(conteudos_e_comandos_ao_banco != null)
				{
					conteudos_e_comandos_ao_banco.close();
				}
				if(conexao_atual_com_banco != null)
				{
					conexao_atual_com_banco.close();
				}
			}
			catch(Exception exite_algum_erro_relatado_na_operacao_fechamento)
			{
				exite_algum_erro_relatado_na_operacao_fechamento.printStackTrace();
			}
		}
	}
 
	public void atualizar(MapeiaTabelaContato registro_em_memoria)
	{

		String sql                                      = "UPDATE contatos SET nome = ?, idade = ?, dataCadastro = ?" + " WHERE id = ?";
		Connection conexao_atual_com_banco              = null;
		PreparedStatement conteudos_e_comandos_ao_banco = null;

		try
		{
			//Cria uma conexão com o banco
			conexao_atual_com_banco = ConnectionFactory.criarConexaoBancoMySQL();

			//Cria um PreparedStatment, classe usada para executar a query
			conteudos_e_comandos_ao_banco = conexao_atual_com_banco.prepareStatement(sql);

			//Adiciona o valor do primeiro parâmetro da sql
			conteudos_e_comandos_ao_banco.setString(1, registro_em_memoria.obtemNome());
			//Adicionar o valor do segundo parâmetro da sql
			conteudos_e_comandos_ao_banco.setInt(2, registro_em_memoria.obtemIdade());
			//Adiciona o valor do terceiro parâmetro da sql
			conteudos_e_comandos_ao_banco.setDate(3, new Date(registro_em_memoria.obtemDataCadastro().getTime()));

			conteudos_e_comandos_ao_banco.setInt(4, registro_em_memoria.obtemCpf());

			//Executa a sql para inserção dos dados
			conteudos_e_comandos_ao_banco.execute();
		}
		catch(Exception exite_algum_erro_relatado_na_operacao_execucao)
		{
			exite_algum_erro_relatado_na_operacao_execucao.printStackTrace();
		}
		finally
		{
			//Fecha as conexões
			try
			{
				if(conteudos_e_comandos_ao_banco != null)
				{
					conteudos_e_comandos_ao_banco.close();
				}
				if(conexao_atual_com_banco != null)
				{
					conexao_atual_com_banco.close();
				}
			}
			catch(Exception exite_algum_erro_relatado_na_operacao_fechamento)
			{
				exite_algum_erro_relatado_na_operacao_fechamento.printStackTrace();
			}
		}
	}
 
	public List<MapeiaTabelaContato> pesquisarContatos()
	{

		String                        sql                           = "SELECT * FROM contatos";
		List<MapeiaTabelaContato>     contatos                      = new ArrayList<MapeiaTabelaContato>();
		Connection                    conexao_atual_com_banco       = null;
		PreparedStatement             conteudos_e_comandos_ao_banco = null;
		//Classe que vai recuperar os dados do banco de dados
		ResultSet                     registro_vindo_do_banco       = null;

		try
		{
			conexao_atual_com_banco       = ConnectionFactory.criarConexaoBancoMySQL();
			conteudos_e_comandos_ao_banco = conexao_atual_com_banco.prepareStatement(sql);
			registro_vindo_do_banco       = conteudos_e_comandos_ao_banco.executeQuery();

			//Enquanto existir dados no banco de dados, faça
			while(registro_vindo_do_banco.next())
			{
				MapeiaTabelaContato contato = new MapeiaTabelaContato();

				//Recupera o id do banco e atribui ele ao objeto
				contato.setId(registro_vindo_do_banco.getInt("id"));

				//Recupera o nome do banco e atribui ele ao objeto
				contato.setNome(registro_vindo_do_banco.getString("nome"));

				//Recupera a idade do banco e atribui ele ao objeto
				contato.setIdade(registro_vindo_do_banco.getInt("idade"));

				//Recupera a data do banco e atribui ela ao objeto
				contato.setDataCadastro(registro_vindo_do_banco.getDate("dataCadastro"));

				//Adiciono o contato recuperado, a lista de contatos
				contatos.add(contato);
			}
		}
		catch (Exception exite_algum_erro_relatado_na_operacao_execucao)
		{
			exite_algum_erro_relatado_na_operacao_execucao.printStackTrace();
		}
		finally
		{
			try
			{
				if(registro_vindo_do_banco != null)
				{
					registro_vindo_do_banco.close();
				}
				if(conteudos_e_comandos_ao_banco != null)
				{
					conteudos_e_comandos_ao_banco.close();
				}
				if(conexao_atual_com_banco != null)
				{
					conexao_atual_com_banco.close();
				}
			}
			catch(Exception exite_algum_erro_relatado_na_operacao_fechamento)
			{
				exite_algum_erro_relatado_na_operacao_fechamento.printStackTrace();
			}
		}
		return contatos;
	}
}
```

## 1.5 - Criar uma classe de interface com o usuário.
Finalmente vamos criar uma classe que será nossa Interface com o usuário, contendo uma função main() com um fluxo principal de execução. Essa classe irá executar uma operação de inserção de registro, uma de alteração e outra de pesquisa de um registro por parâmetro.
arquivo **TesteAgenda.java**
```java

import java.util.Date;
import GerenciaOperacoesCadastro;
import MapeiaTabelaContato;

public class TesteAgenda 
{
	public static void main(String args[])
	{
		GerenciaOperacoesCadastro gerenciador_operacoes = new GerenciaOperacoesCadastro();

		//Cria um contato e salva no banco
		MapeiaTabelaContato contato = new MapeiaTabelaContato();
		contato.setNome("Fulano de Tal");
		contato.setIdade(18);
		contato.setDataCadastro(new Date());

		gerenciador_operacoes.inserir(contato);
		
		//Atualiza o contato com id = 1 com os dados do objeto contato1
		MapeiaTabelaContato contato1 = new MapeiaTabelaContato();
		contato1.setId(1);
		contato1.setNome("Ciclano de tal");
		contato1.setIdade(30);
		contato1.setDataCadastro(new Date());

		gerenciador_operacoes.atualizar(contato1);

		//Remove o contato com id = 1
		gerenciador_operacoes.removePorCpf(1);

		//Lista todos os contatos do banco de dados
		for(MapeiaTabelaContato contador : gerenciador_operacoes.pesquisarContatos())
		{
			System.out.println("NOME: " + contador.obtemNome());
		}
	} // fim do método principal
} // fim da classe Teste Agenda
```

