package dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;

@Named
public class DaoGenerico<E> implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private HibernateUtil hibernateUtil;
	
	// SALVAR
	public void salvar(E entidade){
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		entityManager.persist(entidade);
		transicao.commit();
		
		
	}
	
	// EDITAR
	public E editar(E entidade){
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		
		E e = entityManager.merge(entidade);
				
		transicao.commit();
		
		return e;
	}
	
	// DELETAR
	public void deletar(E entidade){
		
		Object id = hibernateUtil.getPrimaryKey(entidade);
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		
		entityManager.createNativeQuery("DELETE FROM "+ entidade.getClass().getSimpleName().toLowerCase()+
				" WHERE id = " + id).executeUpdate();
		
		transicao.commit();
		
	}
	
	// LISTA ENTIDADE
	public List<E> listar(Class<E> entidade){
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		
		List<E> lista = entityManager.createQuery("FROM "+ entidade.getName()).getResultList();
		
		transicao.commit();
		
		
		return lista;
	}
	
	// LISTA ENTIDADE Limit
		public List<E> listar(Class<E> entidade, int limit){
			
			EntityTransaction transicao = entityManager.getTransaction();
			transicao.begin();
			
			List<E> lista = entityManager.createQuery("FROM "+ entidade.getName() + " ORDER BY id DESC ")
					.setMaxResults(limit)
					.getResultList();
			
			transicao.commit();
			
			
			return lista;
		}
	
	
	
	// PESQUISAR
	public E pesquisar(E entidade){
		
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		
		Object id = hibernateUtil.getPrimaryKey(entidade);

		E e = (E) entityManager.find(entidade.getClass(), id);
		
		transicao.commit();
		return e;
	}
	
	public E pesquisar(Long id, E entidade){
		
		EntityTransaction transicao = entityManager.getTransaction();
		transicao.begin();
		
		E e = (E) entityManager.find(entidade.getClass(), id);
		
		transicao.commit();
		
		return e;
	}
	
	 
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
