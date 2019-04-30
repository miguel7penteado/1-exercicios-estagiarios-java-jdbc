
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
