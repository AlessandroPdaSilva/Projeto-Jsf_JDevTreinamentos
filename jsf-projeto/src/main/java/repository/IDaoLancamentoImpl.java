package repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import conexao.HibernateUtil;
import model.Lancamento;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento,Serializable{
	
	 
	private static final long serialVersionUID = 1L;

	@Inject
	private HibernateUtil hibernateUtil;

	// conexao
	@Inject
	private EntityManager entityManager;
	
	@Override
	public List<Lancamento> consulta(Long idUsuario) {
		
		List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
		
		listaLancamento = (List<Lancamento>) entityManager.createQuery(
				"SELECT l FROM Lancamento l WHERE usuario.id = '"+ idUsuario + "'").getResultList();
		
		return listaLancamento;
	}
	
	@Override
	public List<Lancamento> consultaLimit10(Long idUsuario) {
		
		List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
		
		listaLancamento = (List<Lancamento>) entityManager.createQuery(
				"SELECT l FROM Lancamento l WHERE usuario.id = '"+ idUsuario + "' ORDER BY id DESC ")
				.setMaxResults(10)
				.getResultList();
		
		return listaLancamento;
	}

	@Override
	public List<Lancamento> consultaByData(Date dataInicial, Date dataFinal) {
		
		
		List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dataFinal == null) {
			dataFinal = new Date();
		}
		
		if(dataInicial == null) {
			dataInicial = new Date(0, 0, 1);
		}
		
		listaLancamento = (List<Lancamento>) entityManager.createQuery(
				"SELECT l FROM Lancamento l WHERE dataInicial >= '"+formato.format(dataInicial)
				+"' AND dataFinal <= '"+formato.format(dataFinal) +"' ")
				.getResultList();
		
		return listaLancamento;
	}

}
