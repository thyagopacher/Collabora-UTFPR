package businessService.CRUDAprendizagem.pesquisaEmenta;

import java.sql.ResultSet;

import modelObjects.Ementa;


public interface IPesqEmenta {

	public Ementa pesquisar(Ementa ementa);
	
	public Ementa pesquisar(int idEmenta);
	
	public Ementa transformar(ResultSet rs);
	
}
