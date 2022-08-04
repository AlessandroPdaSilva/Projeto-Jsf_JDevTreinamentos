package repository;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import model.Pessoa;

public interface IDaoPessoa {
	
	Pessoa consultaPessoa(String login, String senha);
	List<SelectItem> listaEstados();
	
	List<Pessoa> consultaByData(Date dataNascInicial, Date dataNascFinal, String nome);
}
