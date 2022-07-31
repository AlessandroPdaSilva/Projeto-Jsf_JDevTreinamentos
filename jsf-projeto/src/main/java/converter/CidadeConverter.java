package converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import conexao.HibernateUtil;
import model.Cidade;


@FacesConverter(forClass = Cidade.class, value = "cidadeConverter")
public class CidadeConverter implements Serializable, Converter {
 
	private static final long serialVersionUID = 1L;

	@Inject
	private HibernateUtil hibernateUtil;
	
	@Override // RETORNA OBJETO
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
		EntityManager entityManager = hibernateUtil.getEntityManager();
		EntityTransaction transacao = entityManager.getTransaction();
		transacao.begin();
		
		Cidade cidade = entityManager.find(Cidade.class, Long.parseLong(codigoCidade));
 
		return cidade;
	}

	@Override // RETORNA STRING
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {

		if(cidade == null) {
			return null;
		}
		
		if(cidade instanceof Cidade){
			return ((Cidade)cidade).getId().toString();
		}else {
			return cidade.toString();
		}
		
	}

}
