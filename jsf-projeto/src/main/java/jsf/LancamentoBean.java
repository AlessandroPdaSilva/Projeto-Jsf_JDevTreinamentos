package jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import dao.DaoGenerico;
import model.Lancamento;
import model.Pessoa;
import repository.IDaoLancamento;
import repository.IDaoLancamentoImpl;
 

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {
	
	private Lancamento lancamento = new Lancamento();
	DaoGenerico<Lancamento> daoLancamento = new DaoGenerico<Lancamento>();
	List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
	IDaoLancamento iDaoLancamento = new IDaoLancamentoImpl();
	
	// SALVAR
	public String salvar(){
		 
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		
	 	Pessoa p = (Pessoa) external.getSessionMap().get("usuarioLogado");
		
	 	lancamento.setUsuario(p);
	 	
	 	daoLancamento.salvar(lancamento);
		
	 	listaLancamento();
		return "";
	}

	// NOVO
	public String novo(){
		lancamento = new Lancamento();
		return "";
	}
	
	//DELETAR
	public String deletar(){
		daoLancamento.deletar(lancamento);
		novo();
		listaLancamento();
		return "";
	}
		
	//LISTAR
	@PostConstruct
	public void listaLancamento(){
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		
	 	Pessoa p = (Pessoa) external.getSessionMap().get("usuarioLogado");
	 
	 	listaLancamento = iDaoLancamento.consulta(p.getId());
	 	
	}
		
	
	// GET E SET
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGenerico<Lancamento> getDaoLancamento() {
		return daoLancamento;
	}
	public void setDaoLancamento(DaoGenerico<Lancamento> daoLancamento) {
		this.daoLancamento = daoLancamento;
	}
	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}
	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}
	
	
	
}
