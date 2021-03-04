package businessService.CRUDAprendizagem.manterDisciplina;

import modelObjects.Disciplina;

public interface IMantDisciplina {
	
	public boolean inserir (Disciplina disciplina);
	
	public boolean modificar (Disciplina disciplina);
	
	public boolean remover (Disciplina disciplina);

}
