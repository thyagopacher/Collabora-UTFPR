package businessService.login.Login;

import businessService.login.pesquisarAluno.IPesquisarAluno;
import businessService.login.pesquisarGerente.IProcurarGerente;
import businessService.login.pesquisarProfessor.IPesquisarProfessor;
import modelObjects.Gerente;
import modelObjects.Professor;
import modelObjects.Usuario;
import modelObjects.Aluno;

public class Login implements ILogin, IProcurarGerente, IPesquisarAluno, IPesquisarProfessor{
	
	public String logar(Usuario user){
		
		Gerente ger = new Gerente();
		Aluno aluno = new Aluno();
		Professor professor = new Professor();
		
		ger.setNome(user.getNome());
		ger.setSenha(user.getSenha());
		if(pesqGerente.pesquisar(ger) != null){
			return GERENTE;
		}
		
		
		
		aluno.setNome(user.getNome());
		aluno.setSenha(user.getSenha());
		if(pesqAluno.pesquisar(aluno) != null){
			return ALUNO;
		}
		
		professor.setNome(user.getNome());
		professor.setSenha(user.getSenha());
		if(pesqProf.pesquisar(professor)!= null){
			return PROFESSOR;
		}
		
		return "error";
		
	}

}
