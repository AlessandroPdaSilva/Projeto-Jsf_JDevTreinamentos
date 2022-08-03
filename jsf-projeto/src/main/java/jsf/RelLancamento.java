package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.DaoGenerico;
import model.Lancamento;

@ViewScoped
@Named
public class RelLancamento implements Serializable{
 
	private static final long serialVersionUID = 1L;

	List<Lancamento> listaLancamento = new ArrayList<Lancamento>();

	@Inject
	DaoGenerico<Lancamento> daoLancamento;
	
	
	public String buscar(){
		
		listaLancamento = daoLancamento.listar(Lancamento.class, 10);
		
		return "";
	}
	
	
	// GET E SET
	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}
	
	
	
}
