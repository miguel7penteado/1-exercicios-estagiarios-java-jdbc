 
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
