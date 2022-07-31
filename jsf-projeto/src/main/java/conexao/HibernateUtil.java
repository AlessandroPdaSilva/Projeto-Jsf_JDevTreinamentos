package conexao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class HibernateUtil {
	
	public static EntityManagerFactory factory = null;
	
	public HibernateUtil() {
		if(factory == null) {
			factory = Persistence.createEntityManagerFactory("jsf-projeto");
		}
	}
	 
	
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	// RETORNA CHAVE PRIMARIA
	public Object getPrimaryKey(Object entidade) { 
		return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
	
	
}
