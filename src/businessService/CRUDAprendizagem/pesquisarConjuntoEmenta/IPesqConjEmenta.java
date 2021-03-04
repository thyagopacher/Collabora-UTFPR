package businessService.CRUDAprendizagem.pesquisarConjuntoEmenta;

import java.sql.ResultSet;
import java.util.ArrayList;

import modelObjects.Disciplina;
import modelObjects.Ementa;



public interface IPesqConjEmenta {
	public ArrayList<Ementa> transformar (ResultSet rs);
	
	public boolean pesquisar(ArrayList<Ementa> ementas);
	
	public ArrayList<Ementa> pesquisarTodos();
	
	public ArrayList<Ementa> pesquisar (Disciplina disciplina);
	
	public ArrayList<Ementa> transformar (ResultSet rs, Disciplina disciplina);

}
