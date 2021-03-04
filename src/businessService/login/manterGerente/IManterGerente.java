package businessService.login.manterGerente;

import java.util.List;

import modelObjects.Gerente;

public interface IManterGerente {
	
	public boolean inserir (Gerente gerente);
	
	public boolean inserir (List<Gerente> lista);
	
	public boolean modificar (Gerente gerente);
	
	public boolean remover (Gerente gerente);

}
