package businessService.CRUDAprendizagem.manterTurma;

import modelObjects.Turma;



public interface IMantTurma {
	
	public boolean inserir (Turma turma);
	
	public boolean modificar (Turma turma);
	
	public boolean remover (Turma turma);
	
	public boolean inserirAlunosTurma(Turma turma);
	
	public boolean modificarAlunosTurma(Turma turma);
	
	public boolean removerAlunosTurma(Turma turma);

}
