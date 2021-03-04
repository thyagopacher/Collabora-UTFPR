package businessService.login.pesquisarGerente;

import java.sql.ResultSet;

import modelObjects.Gerente;

public interface IPesquisarGerente {
	
	public Gerente pesquisar(int id);
	
	public Gerente transformar (ResultSet rs);

}
