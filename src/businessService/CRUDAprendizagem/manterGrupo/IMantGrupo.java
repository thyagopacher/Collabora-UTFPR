package businessService.CRUDAprendizagem.manterGrupo;

import modelObjects.Grupo;

public interface IMantGrupo {

	public boolean inserir (Grupo grupo);
	
	public boolean modificar (Grupo grupo);
	
	public boolean remover (Grupo grupo);
}
