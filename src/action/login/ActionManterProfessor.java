package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterProfessor.IManterProfessor;
import modelObjects.Professor;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de CRUD para o professor (IManterProfessor)
 * 	- Que oferece o seri�o interface com a vis�o (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionManterProfessor extends ActionSupport implements ModelDriven<Professor>, 
	IManterProfessor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Professor professor = new Professor();
	private String confirmacao = new String();
	private String button;
	
	/*
	 * 		Validate (M�todo Original da classe ActionSupport)
	 * 
	 * 	Verifica se os campos foram preenchidos pelo usu�rio
	 * 
	 * 	Este m�todo � chamado antes do execute e depois do getModel. 
	 * 	Uma vez que ele confere os dados que o execute ir� acessar e necessita
	 * 	dos dados que ser�o populados pelo getModel.
	 * 
	 * 	Coloca mensagens de erro nos campos caso o usu�rio n�o tenha preenchido 
	 * os respectivos campos.
	 * */
	@Override
	public void validate() {
		
		//�nica verifica��o para excluir � possuir o id
		if("Excluir".equals(getButton())){
			
			if( professor.getId() == null)
				addActionMessage("Voc� deve selecionar o professor para poder exclui-lo!");	
			
			return;
		}
		
		//demais verifica��es para a inser��o e modifica��o dos dados
		if(getProfessor().getNome().isEmpty()) {
			addFieldError("professor.nome", "O campo nome n�o pode ser vazio");
			return;
		}
		
		if(getProfessor().getRegistro().isEmpty()){
			addFieldError("professor.registro", "O campo registro n�o pode ser vazio");
			return;
		}
		
		if(getProfessor().getSenha().isEmpty()){
			addFieldError("professor.senha", "O campo senha n�o pode ser vazio");
			return;
		}
		
		if(confirmacao.isEmpty())
			addFieldError("confirmacao", "O campo confirmacao n�o pode ser vazio");
    }
	
	/*
	 * 		Execute(M�todo Original da classe ActionSupport)
	 * 
	 * M�todo default do mapeamento do struts2.
	 * 
	 * 	Realiza verifica��es quanto ao qual a��o o usu�rio deseja executar
	 * (inclus�o, exclus�o ou inser��o) e chama o m�todo respons�vel pelo mesmo
	 * 
	 * 	Adiociona uma mensagem de erro ao campo confirma��o caso as senhas
	 * forem diferentes e retorna erro.
	 * 	Verifica A op��o escolhida pelo usuario pela variavel button, que 
	 * recebe o nome do bot�o pressionado. 
	 * 
	 * */
	@Override
	public String execute(){
		
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(getConfirmacao().equals(getProfessor().getSenha())){
		
			if(professor.getId() == null || professor.getId().equals(0)){
				return this.Insert();
			}
			else{
				return this.Update();	
			}
		}
	
		addFieldError("confirmacao", "As senhas devem ser iguais, verifique!");
		return ERROR;
		
	}
	
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remo��o do professor:
	 * 	-caso for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de A��o, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Delete() {
		
		if(mantProfessor.remover(professor)){
			addActionMessage("Professor removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR; 
	}
	
	/*
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inser��o do professor:
	 * 	-caso for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de A��o, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Insert(){
		
		if(mantProfessor.inserir(professor)){
			addActionMessage("Professor inserido com sucesso");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel inserir este professor, verifique!");
		return ERROR;
		
		
	}
	
	/*
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modifica��o do professor:
	 * 	-caso for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de A��o, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Update(){
		
		if(mantProfessor.modificar(professor)){
			addActionMessage("Professor modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
		
	}
	
	//limpa os campos do model e tamb�m do atributo confirma��o
	public void Clean(){
		getProfessor().setNome("");
		getProfessor().setSenha("");
		getProfessor().setRegistro("");
		getProfessor().setId(0);
		confirmacao = "";
		
	}
	
	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}

	@Override
	public Professor getModel() {
		// TODO Auto-generated method stub
		return getProfessor();
	}
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

}
