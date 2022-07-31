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
		
		
		entityManager.persist(entidade);
				
		
	}
	
	// EDITAR
	public E editar(E entidade){
		
		E e = entityManager.merge(entidade);
				
		return e;
	}
	
	// DELETAR
	public void deletar(E entidade){
		
		Object id = hibernateUtil.getPrimaryKey(entidade);
		
		
		entityManager.createNativeQuery("DELETE FROM "+ entidade.getClass().getSimpleName().toLowerCase()+
				" WHERE id = " + id).executeUpdate();
		
		
	}
	
	// LISTA ENTIDADE
	public List<E> listar(Class<E> entidade){
		
		
		List<E> lista = entityManager.createQuery("FROM "+ entidade.getName()).getResultList();
		
		return lista;
	}
	
	// PESQUISAR
	public E pesquisar(E entidade){
		
		Object id = hibernateUtil.getPrimaryKey(entidade);

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
