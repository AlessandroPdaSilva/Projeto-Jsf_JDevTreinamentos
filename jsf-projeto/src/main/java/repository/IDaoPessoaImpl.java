package repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Estado;
import model.Lancamento;
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
		 
		try {
			p = (Pessoa) entityManager.createQuery("SELECT p FROM Pessoa p WHERE login = '" + login + "' "
					+ "AND senha = '"+senha+"'").getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		
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

	@Override
	public List<Pessoa> consultaByData(Date dataNascInicial, Date dataNascFinal, String nome) {
		
		
		List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dataNascFinal == null) {
			dataNascFinal = new Date();
		}
		
		if(dataNascInicial == null) {
			dataNascInicial = new Date(0, 0, 1);
		}
		
		
		String sql = "SELECT p FROM Pessoa p WHERE dataNascimento >= '"+formato.format(dataNascInicial)
		+"' AND dataNascimento <= '"+formato.format(dataNascFinal) +"' ";
		
		
		if(nome != null) {
			sql += "AND nome LIKE '%"+nome+"%'";
		}
		
		listaPessoa = (List<Pessoa>) entityManager.createQuery(sql)
				.getResultList();
		
		return listaPessoa;
	}

	

}
