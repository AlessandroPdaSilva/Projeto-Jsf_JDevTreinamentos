package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Pessoa;

public class IDaoPessoaImpl implements IDaoPessoa{
	
	// conexao
	private EntityManager entityManager  = HibernateUtil.getEntityManager();

	@Override
	public Pessoa consultaPessoa(String login, String senha) {
		
		Pessoa p = new Pessoa();
		 
		p = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE login = '" + login + "' "
				+ "AND senha = '"+senha+"'").getSingleResult();
		
		 
		
		return p;
	}

}
