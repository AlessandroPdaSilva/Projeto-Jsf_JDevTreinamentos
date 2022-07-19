package jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoGenerico;
import model.Lancamento;
import model.Pessoa;
 

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {
	
	private Lancamento lancamento;
	DaoGenerico<Lancamento> daoPessoa = new DaoGenerico<Lancamento>();
	List<Lancamento> listaPessoa = new ArrayList<Lancamento>();
	
	// SALVAR
	public String salvar(){
		 
		return "";
	}

	// NOVO
	public String novo(){
	 
		return "";
	}
	
	//DELETAR
	public String deletar(){
	 
		return "";
	}
		
	
	
	// GET E SET
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGenerico<Lancamento> getDaoPessoa() {
		return daoPessoa;
	}
	public void setDaoPessoa(DaoGenerico<Lancamento> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}
	public List<Lancamento> getListaPessoa() {
		return listaPessoa;
	}
	public void setListaPessoa(List<Lancamento> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
	
	
	
	
}
