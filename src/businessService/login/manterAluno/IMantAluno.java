package businessService.login.manterAluno;

import java.util.List;

import modelObjects.Aluno;

public interface IMantAluno {
	
	public boolean inserir (Aluno aluno);
	
	public boolean inserir (List<Aluno> lista);
	
	public boolean modificar (Aluno aluno);
	
	public boolean remover (Aluno aluno);

}
