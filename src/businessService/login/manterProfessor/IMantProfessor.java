package businessService.login.manterProfessor;

import java.util.List;

import modelObjects.Professor;

public interface IMantProfessor {

	public boolean inserir (Professor professor);
	
	public boolean modificar (Professor professor);
	
	public boolean remover (Professor professor);
	
	public boolean inserir (List<Professor> professores);

}
