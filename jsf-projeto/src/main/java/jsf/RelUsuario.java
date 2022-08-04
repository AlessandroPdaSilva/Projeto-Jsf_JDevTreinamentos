package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import conexao.HibernateUtil;
import dao.DaoGenerico;
import model.Lancamento;
import model.Pessoa;
import repository.IDaoLancamento;
import repository.IDaoPessoa;
import repository.IDaoPessoaImpl;

@ViewScoped
@Named
public class RelUsuario implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	@Inject
	private DaoGenerico<Pessoa> daoPessoa;
	
	@Inject
	private IDaoPessoa idaoPessoa;
	
	@Inject
	private HibernateUtil hibernateUtil;
	
	private List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
	private Date dataNascInicial;
	private Date dataNascFinal;
	private String nome;
	
	
	public String buscar(){
		
		if(dataNascInicial == null && dataNascFinal == null && nome == null) {
			listaPessoa = daoPessoa.listar(Pessoa.class, 10);
		}else {
			listaPessoa = idaoPessoa.consultaByData(dataNascInicial, dataNascFinal, nome);
		}
		
		return "";
	}
	
	
	// GET E SET
	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}
	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascInicial() {
		return dataNascInicial;
	}
	public void setDataNascInicial(Date dataNascInicial) {
		this.dataNascInicial = dataNascInicial;
	}
	public Date getDataNascFinal() {
		return dataNascFinal;
	}
	public void setDataNascFinal(Date dataNascFinal) {
		this.dataNascFinal = dataNascFinal;
	}
	
	
	
	
	
}
