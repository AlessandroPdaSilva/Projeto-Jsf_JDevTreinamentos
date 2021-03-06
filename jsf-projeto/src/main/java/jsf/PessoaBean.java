package jsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

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
	
	
	// PESQUISAR CEP
	public void pesquisarCep(AjaxBehaviorEvent event){
		
		try {
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");// URL
			URLConnection buscaUrl = url.openConnection();// abrindo URL
			InputStream respUrl = buscaUrl.getInputStream();// resultado URL
			BufferedReader br = new BufferedReader(new InputStreamReader(respUrl,"UTF-8"));// resultado Json
			
			// jogando Json em uma String
			StringBuilder jsonCep = new StringBuilder();
			
			String aux = "";
			while((aux = br.readLine()) != null) {
				jsonCep.append(aux);
			}
			
			// jogando para dentro da classe
			Pessoa pAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setLocalidade(pAux.getLocalidade());
			pessoa.setLogradouro(pAux.getLogradouro());
			pessoa.setBairro(pAux.getBairro());
			pessoa.setUf(pAux.getUf());
			
			System.out.println(jsonCep);
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("CEP invalido");
		}
	}
	
	
	// MOSTRAR MENSAGEM
	private void mostrarMsg(String msg) {
		 FacesContext contexto = FacesContext.getCurrentInstance();//pagina
		 FacesMessage mensagem = new FacesMessage(msg);// mensagem da pagina
		 contexto.addMessage(null,mensagem);// adicionando
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
	
	// DESLOGAR
	public String deslogar(){
		
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		external.getSessionMap().remove("usuarioLogado");
		
		HttpServletRequest request = (HttpServletRequest) 
				context.getCurrentInstance().getExternalContext().getRequest();
		
		request.getSession().invalidate();
		
		
		return "index.jsp";
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
