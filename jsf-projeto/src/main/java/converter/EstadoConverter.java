package converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Estado;

 
@FacesConverter(forClass = Estado.class, value = "estadoConverter")
public class EstadoConverter implements Serializable, Converter {
 
	private static final long serialVersionUID = 1L;

	@Inject
	private HibernateUtil hibernateUtil;
	
	@Override // RETORNA OBJETO
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = hibernateUtil.getEntityManager();
		EntityTransaction transacao = entityManager.getTransaction();
		transacao.begin();
		
		Estado estado = entityManager.find(Estado.class, Long.parseLong(codigoEstado));

		return estado;
	}

	@Override // RETORNA STRING
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		if(estado == null) {
			return null;
		}
		
		if(estado instanceof Estado){
			return ((Estado)estado).getId().toString();
		}else {
			return estado.toString();
		}
		
		
	}

}
