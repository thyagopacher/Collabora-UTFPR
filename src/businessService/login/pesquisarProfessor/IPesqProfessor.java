package businessService.login.pesquisarProfessor;

import java.sql.ResultSet;
import java.util.ArrayList;

import modelObjects.Professor;

public interface IPesqProfessor {
	
	public Professor pesquisar(String registro);
	
	public ArrayList<Professor> pequisarTodos();
	
	public Professor pesquisarPorId (int idProfessor);
	
	public Professor pesquisar (Professor professor);
	
	public Professor transformar(ResultSet rs);
}
