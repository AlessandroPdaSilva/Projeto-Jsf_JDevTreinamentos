package repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Estado;
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

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> listaSelectItem = new ArrayList<SelectItem>();
		
		List<Estado> estados = entityManager.createQuery("from Estado").getResultList();
		
		for(Estado e: estados) {
			listaSelectItem.add(new SelectItem(e,e.getNome()));
		}
		
		return listaSelectItem;
	}

}
