package jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
		mostrarMsg("Cadastrado com sucesso!!");
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
		mostrarMsg("Deletado com sucesso!!");
		return "";
	}
	
	//LISTAR
	@PostConstruct
	public void lista(){
		listaPessoa = daoPessoa.listar(Pessoa.class);
	}
	
	// MOSTRAR MENSAGEM
	private void mostrarMsg(String msg) {
		 FacesContext contexto = FacesContext.getCurrentInstance();
		 FacesMessage mensagem = new FacesMessage(msg);
		 contexto.addMessage(null,mensagem);
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
	
	// PERMITE ACESSO
	public Boolean permiteAcesso(String perfilAcesso){
		
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		
	 	Pessoa p =(Pessoa) external.getSessionMap().get("usuarioLogado");
		
		return p.getPerfil().equals(perfilAcesso);
		
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
