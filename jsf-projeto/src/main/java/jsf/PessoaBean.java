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

import dao.DaoGenerico;
import model.Pessoa;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean {

	Pessoa pessoa = new Pessoa();
	DaoGenerico<Pessoa> daoPessoa = new DaoGenerico<Pessoa>();
	List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
	
	public String salvar(){
		pessoa = daoPessoa.editar(pessoa);
		lista();
		return "";
	}

	public String novo(){
		pessoa = new Pessoa();
		return "";
	}
	
	public String deletar(){
		daoPessoa.deletar(pessoa);
		novo();
		lista();
		return "";
	}
	
	@PostConstruct
	public void lista(){
		listaPessoa = daoPessoa.listar(Pessoa.class);
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
