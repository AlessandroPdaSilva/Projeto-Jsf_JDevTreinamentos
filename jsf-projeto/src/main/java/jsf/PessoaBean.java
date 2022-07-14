package jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import dao.DaoGenerico;
import model.Pessoa;
import repository.IDaoPessoa;
import repository.IDaoPessoaImpl;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean {

	Pessoa pessoa = new Pessoa();
	DaoGenerico<Pessoa> daoPessoa = new DaoGenerico<Pessoa>();
	List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
	IDaoPessoa idaoPessoa = new IDaoPessoaImpl();
	
	
	// SALVAR
	public String salvar(){
		pessoa = daoPessoa.editar(pessoa);
		lista();
		return "";
	}

	// NOVO
	public String novo(){
		pessoa = new Pessoa();
		return "";
	}
	
	//DELETAR
	public String deletar(){
		daoPessoa.deletar(pessoa);
		novo();
		lista();
		return "";
	}
	
	//LISTAR
	@PostConstruct
	public void lista(){
		listaPessoa = daoPessoa.listar(Pessoa.class);
	}
	
	//LOGAR
	public String logar(){
		
		 
		Pessoa p = idaoPessoa.consultaPessoa(pessoa.getLogin(), pessoa.getSenha());
		
		if(p != null) {
			
			// adicionar na sessao
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			
			external.getSessionMap().put("usuarioLogado", p);
			
			return "primeirapagina.jsf";
		}else {
			return "index.jsf";
		}
		
	}
	
	
	
	
	
	// GET E SET
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGenerico<Pessoa> getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(DaoGenerico<Pessoa> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	
	
}
