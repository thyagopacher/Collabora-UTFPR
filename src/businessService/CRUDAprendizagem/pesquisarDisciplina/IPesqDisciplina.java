package businessService.CRUDAprendizagem.pesquisarDisciplina;

import java.sql.ResultSet;
import java.util.List;

import modelObjects.Disciplina;

public interface IPesqDisciplina {
	
	public Disciplina pesquisar(int id);
	
	public Disciplina pesquisar(String nome, String codigo);
	
	public List<Disciplina> pesquisaTodos();
	
	public List<Disciplina> transformarConjunto(ResultSet rs);
	
	public Disciplina transformar(ResultSet rs);

}
