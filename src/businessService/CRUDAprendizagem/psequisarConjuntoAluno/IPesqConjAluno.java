package businessService.CRUDAprendizagem.psequisarConjuntoAluno;

import java.sql.ResultSet;
import java.util.List;

import modelObjects.Aluno;



public interface IPesqConjAluno {
	
	public List<Aluno> transformar(ResultSet rs);
	
	public boolean pesquisar(List<Aluno> alunos);
	
	public List<Aluno> pesquisarTodos();
}
