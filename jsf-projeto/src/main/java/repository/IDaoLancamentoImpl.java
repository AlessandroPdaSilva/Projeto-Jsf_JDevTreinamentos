package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import conexao.HibernateUtil;
import model.Lancamento;
import model.Pessoa;

public class IDaoLancamentoImpl implements IDaoLancamento{

	// conexao
	private EntityManager entityManager  = HibernateUtil.getEntityManager();
	
	@Override
	public List<Lancamento> consulta(Long idUsuario) {
		
		List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
		
		listaLancamento = (List<Lancamento>) entityManager.createQuery("SELECT l FROM Lancamento l WHERE usuario.id = '"+ idUsuario + "'").getResultList();
		
		return listaLancamento;
	}

}
