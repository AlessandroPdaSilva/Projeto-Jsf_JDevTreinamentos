package teste;

import javax.persistence.Persistence;

import org.junit.Test;

import dao.DaoGenerico;
import model.Pessoa;


public class Teste {
	
	@Test
	public void testeMain() {
		DaoGenerico<Pessoa> daoPessoa = new DaoGenerico<Pessoa>();
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("sdfsdf");
		pessoa.setSobrenome("sdfsdf");
		pessoa.setIdade(12);
		pessoa.setDataNascimento(null);
		
		daoPessoa.salvar(pessoa);
	}
	
	@Test
	public void teste(){
		Persistence.createEntityManagerFactory("jsf-projeto");
		
	}
	
}
