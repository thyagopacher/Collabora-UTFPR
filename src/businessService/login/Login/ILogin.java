package businessService.login.Login;

import modelObjects.Usuario;

public interface ILogin {
	
	public static final String GERENTE = "gerente";
	public static final String ALUNO = "aluno";
	public static final String PROFESSOR = "professor";
	
	public String logar(Usuario user);

}
