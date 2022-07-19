package repository;

import java.util.List;

import model.Lancamento;

public interface IDaoLancamento {
	
	List<Lancamento> consulta(Long idUsuario);
}
