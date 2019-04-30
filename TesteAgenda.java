
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