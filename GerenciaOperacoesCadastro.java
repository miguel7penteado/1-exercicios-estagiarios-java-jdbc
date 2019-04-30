 
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