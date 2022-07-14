package repository;

import model.Pessoa;

public interface IDaoPessoa {
	
	Pessoa consultaPessoa(String login, String senha);
}
