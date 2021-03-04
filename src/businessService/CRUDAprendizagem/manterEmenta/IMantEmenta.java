package businessService.CRUDAprendizagem.manterEmenta;

import modelObjects.Ementa;

public interface IMantEmenta {
	
	public boolean inserir (Ementa ementa);
	
	public boolean modificar (Ementa ementa);
	
	public boolean remover (Ementa ementa);

}
