package businessService.CRUDAprendizagem.manterConteudo;

import modelObjects.Conteudo;

public interface ImantConteudo {
	
	public boolean inserir (Conteudo conteudo);
	
	public boolean modificar (Conteudo conteudo);
	
	public boolean remover (Conteudo conteudo);

}
