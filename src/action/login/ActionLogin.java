package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import br.com.collabora.facades.ManipuladorFacades;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;
import businessService.login.Login.ILogar;
import businessService.login.Login.ILogin;
import modelObjects.Aluno;
import modelObjects.Usuario;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferecem o servi�o para realizar o login (ILogar)
 * 	- Que oferecem o seri�o interface com a vis�o (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionLogin extends ActionSupport implements ModelDriven<Usuario>, ILogar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario user = new Usuario();

	/*
	 * Validate (M�todo Original da classe ActionSupport)
	 * 
	 * Verifica se os campos foram preenchidos pelo usu�rio
	 * 
	 * Coloca mensagens de erro nos campos caso o usu�rio n�o tenha preenchido
	 * os respectivos campos.
	 */
	@Override
	public void validate() {

		if (user.getNome().isEmpty()) {
			addFieldError("nome", "O campo nome de usu�rio n�o pode ser branco");
			return;
		}

		if (user.getSenha().isEmpty()) {
			addFieldError("senha", "O campo senha n�o pode ser branco");
			return;
		}

	}

	/*
	 * Execute(M�todo Original da classe ActionSupport)
	 * 
	 * M�todo default do mapeamento do struts2.
	 * 
	 * Verifica se os dados que est�o contidos em user s�o v�lidos, ou seja,
	 * est�o cadastrados na base de dados.
	 * 
	 * Se o usu�rio � valido, � configurada uma mensagem de a��o e retornado o
	 * tipo de usu�rio.
	 * 
	 * Caso contr�rio � configurada uma mensagem de erro e retornado ERROR
	 */
	@Override
	public String execute() throws Exception {

		String tipo = login.logar(user);

		if (tipo.equals(ERROR)) {
			addActionError("Nome de usu�rio/senha incorretos!");
		}

		else {

			if (ILogin.ALUNO.equals(tipo)) {
				ManipuladorFacades.getControlador(ControladorSessaoAluno.class).atualizarSessao(parseParaAluno());
			}

			addActionMessage("Bem Vindo " + user.getNome().toUpperCase());

			LimparUser();

			return tipo;
		}

		return ERROR;
	}

	private Aluno parseParaAluno() {
		Aluno a = new Aluno();
		a.setNome(user.getNome());
		a.setSenha(user.getSenha());
		return a;
	}

	// limpa os campos do user
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	private void LimparUser() {
		getUser().setNome("");
		getUser().setSenha("");
	}

	@Override
	public Usuario getModel() {
		// TODO Auto-generated method stub
		return getUser();
	}

}
