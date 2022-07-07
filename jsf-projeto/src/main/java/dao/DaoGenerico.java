package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;

public class DaoGenerico<E> {

	 
	private EntityManager entityManager  = HibernateUtil.getEntityManager();
	
	// SALVAR
	public void salvar(E entidade){
		
		EntityTransaction transacao = entityManager.getTransaction();
		
		transacao.begin();
		entityManager.persist(entidade);
				
		transacao.commit();
		
	}
	
	// EDITAR
	public void editar(E entidade){
		
		EntityTransaction transacao = entityManager.getTransaction();
		
		transacao.begin();
		entityManager.merge(entidade);
				
		transacao.commit();
		
	}
	
	// DELETAR
	public void deletar(E entidade){
		
		Object id = HibernateUtil.getPrimaryKey(entidade);
		
		EntityTransaction transacao = entityManager.getTransaction();
		transacao.begin();
		
		entityManager.createNativeQuery("DELETE FROM "+ entidade.getClass().getSimpleName().toLowerCase()+
				" WHERE id = " + id).executeUpdate();
		
		transacao.commit();
		
	}
	
	// LISTA ENTIDADE
	public List<E> listar(Class<E> entidade){
		
		EntityTransaction transacao = entityManager.getTransaction();
		
		transacao.begin();
		
		List<E> lista = entityManager.createQuery("FROM "+ entidade.getName()).getResultList();
		
		transacao.commit();
		
		return lista;
	}
	
	// PESQUISAR
	public E pesquisar(E entidade){
		
		Object id = HibernateUtil.getPrimaryKey(entidade);

		E e = (E) entityManager.find(entidade.getClass(), id);
		
		return e;
	}
	
	public E pesquisar(Long id, E entidade){
		
		E e = (E) entityManager.find(entidade.getClass(), id);
		
		return e;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
