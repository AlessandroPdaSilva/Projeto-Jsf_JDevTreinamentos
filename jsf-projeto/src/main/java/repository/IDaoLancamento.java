package repository;

import java.util.Date;
import java.util.List;

import model.Lancamento;

public interface IDaoLancamento {
	
	List<Lancamento> consulta(Long idUsuario);
	
	List<Lancamento> consultaLimit10(Long limit);
	
	List<Lancamento> consultaByData(Date dataInicial, Date dataFinal);
}
