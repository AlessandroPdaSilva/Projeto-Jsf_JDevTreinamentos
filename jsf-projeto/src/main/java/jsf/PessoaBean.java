package jsf;

import java.util.ArrayList;
import java.util.List;

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
	
	public String salvar(){
		daoPessoa.salvar(pessoa);
		pessoa = new Pessoa();
		return "";
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

	
	
}
