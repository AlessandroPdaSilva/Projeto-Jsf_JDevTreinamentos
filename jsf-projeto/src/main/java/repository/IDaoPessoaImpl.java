package repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Estado;
import model.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa,Serializable{
	 
	private static final long serialVersionUID = 1L;

	@Inject
	private HibernateUtil hibernateUtil;
	
	@Inject
	private EntityManager entityManager;

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
