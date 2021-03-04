package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterAluno.IManterAluno;
import modelObjects.Aluno;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de CRUD para o aluno (IManterAluno)
 * 	- Que oferece o seri�o interface com a vis�o (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionManterAluno extends ActionSupport implements ModelDriven<Aluno>, IManterAluno{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aluno aluno = new Aluno();
	private String confirmacao = new String();
	private String button;


	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	
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
			
			if( aluno.getId() == null)
				addActionMessage("Voc� deve selecionar o aluno para poder exclui-lo!");	
			
			return;
		}
		
		//demais verifica��es para a inser��o e modifica��o dos dados
		if(aluno.getNome().isEmpty()){
			addFieldError("aluno.nome", "O campo n�o pode ser vazio!");
			return;
		}
		
		if(aluno.getRegistro().isEmpty()){
			addFieldError("aluno.regsitro", "O campo n�o pode ser vazio!");
			return;
		}
		
		if(aluno.getSenha().isEmpty()){
			addFieldError("aluno.senha", "O campo n�o pode ser vazio!");
			return;
		}
		
		if(confirmacao.isEmpty()){
			addFieldError("confirmacao", "O campo n�o pode ser vazio!");
			return;
		}
		
		if(!confirmacao.equals(aluno.getSenha()))
			addFieldError("confirmacao", "As senhas s�o diferentes, verifique!");
		
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
		
		
		if(aluno.getSenha().equals(confirmacao)){
			
			if(aluno.getId() == null || aluno.getId().equals(0)){
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
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inser��o do aluno:
	 * 	-caso for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de A��o, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Insert(){
		
		if(mantAluno.inserir(aluno)){
			addActionMessage("Aluno inserido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
	}
	
	/*
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modifica��o do aluno:
	 * 	-caso for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de A��o, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Update(){
		
		if(mantAluno.modificar(aluno)){
			addActionMessage("Aluno modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}
	
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remo��o do aluno:
	 * 	-caso for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de A��o, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Delete(){
		
		if(mantAluno.remover(aluno)){
			addActionMessage("Aluno removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}
	//limpa os campos do model e tamb�m do atributo confirma��o
	public void Clean(){
		aluno.setNome("");
		aluno.setRegistro("");
		aluno.setSenha("");
		setConfirmacao("");
		aluno.setId(0);
	}
	
	@Override
	public Aluno getModel() {
		// TODO Auto-generated method stub
		return aluno;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}

}
