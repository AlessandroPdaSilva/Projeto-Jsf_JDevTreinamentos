package conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	
	public static EntityManagerFactory factory = null;
	
	static {
		init();
	}
	
	public static void init(){
		
		try {
			
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("jsf-projeto");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// GET CONEXAO
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	// RETORNA CHAVE PRIMARIA
	public static Object getPrimaryKey(Object entidade) { 
		return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
	
	
}
