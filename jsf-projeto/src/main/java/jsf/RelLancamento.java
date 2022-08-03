package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.DaoGenerico;
import model.Lancamento;
import repository.IDaoLancamento;

@ViewScoped
@Named
public class RelLancamento implements Serializable{
 
	private static final long serialVersionUID = 1L;

	List<Lancamento> listaLancamento = new ArrayList<Lancamento>();
	private Date dataInicial;
	private Date datafinal;
	
	@Inject
	private IDaoLancamento iDaoLancamento;
	
	@Inject
	private DaoGenerico<Lancamento> daoLancamento;
	
	
	public String buscar(){
		
		if(dataInicial == null && datafinal == null) {
			listaLancamento = daoLancamento.listar(Lancamento.class, 10);
		}else {
			listaLancamento = iDaoLancamento.consultaByData(dataInicial, datafinal);
		}
		
		return "";
	}
	
	
	// GET E SET
	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}
	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDatafinal() {
		return datafinal;
	}
	public void setDatafinal(Date datafinal) {
		this.datafinal = datafinal;
	}
	public IDaoLancamento getiDaoLancamento() {
		return iDaoLancamento;
	}
	public void setiDaoLancamento(IDaoLancamento iDaoLancamento) {
		this.iDaoLancamento = iDaoLancamento;
	}
	
	
}
