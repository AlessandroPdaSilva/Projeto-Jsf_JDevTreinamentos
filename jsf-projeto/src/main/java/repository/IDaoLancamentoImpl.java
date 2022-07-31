package repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import conexao.HibernateUtil;
import model.Lancamento;
import model.Pessoa;

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
		
		listaLancamento = (List<Lancamento>) entityManager.createQuery("SELECT l FROM Lancamento l WHERE usuario.id = '"+ idUsuario + "'").getResultList();
		
		return listaLancamento;
	}

}
