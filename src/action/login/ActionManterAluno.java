package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterAluno.IManterAluno;
import modelObjects.Aluno;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de CRUD para o aluno (IManterAluno)
 * 	- Que oferece o seriço interface com a visão (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
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
	 * 		Validate (Método Original da classe ActionSupport)
	 * 
	 * 	Verifica se os campos foram preenchidos pelo usuário
	 * 
	 * 	Este método é chamado antes do execute e depois do getModel. 
	 * 	Uma vez que ele confere os dados que o execute irá acessar e necessita
	 * 	dos dados que serão populados pelo getModel.
	 * 
	 * 	Coloca mensagens de erro nos campos caso o usuário não tenha preenchido 
	 * os respectivos campos.
	 * */
	@Override
	public void validate() {
		
		//única verificação para excluir é possuir o id
		if("Excluir".equals(getButton())){
			
			if( aluno.getId() == null)
				addActionMessage("Você deve selecionar o aluno para poder exclui-lo!");	
			
			return;
		}
		
		//demais verificações para a inserção e modificação dos dados
		if(aluno.getNome().isEmpty()){
			addFieldError("aluno.nome", "O campo não pode ser vazio!");
			return;
		}
		
		if(aluno.getRegistro().isEmpty()){
			addFieldError("aluno.regsitro", "O campo não pode ser vazio!");
			return;
		}
		
		if(aluno.getSenha().isEmpty()){
			addFieldError("aluno.senha", "O campo não pode ser vazio!");
			return;
		}
		
		if(confirmacao.isEmpty()){
			addFieldError("confirmacao", "O campo não pode ser vazio!");
			return;
		}
		
		if(!confirmacao.equals(aluno.getSenha()))
			addFieldError("confirmacao", "As senhas são diferentes, verifique!");
		
	}
	
	/*
	 * 		Execute(Método Original da classe ActionSupport)
	 * 
	 * Método default do mapeamento do struts2.
	 * 
	 * 	Realiza verificações quanto ao qual ação o usuário deseja executar
	 * (inclusão, exclusão ou inserção) e chama o método responsável pelo mesmo
	 * 
	 * 	Adiociona uma mensagem de erro ao campo confirmação caso as senhas
	 * forem diferentes e retorna erro.
	 * 	Verifica A opção escolhida pelo usuario pela variavel button, que 
	 * recebe o nome do botão pressionado. 
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
	 * 		Tenta realizar a inserção do aluno:
	 * 	-caso for possível realizar a inserção
	 * 		-Adicona uma mensagem de Ação, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a inserção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Insert(){
		
		if(mantAluno.inserir(aluno)){
			addActionMessage("Aluno inserido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a inserção");
		return ERROR;
	}
	
	/*
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modificação do aluno:
	 * 	-caso for possível realizar a modificação
	 * 		-Adicona uma mensagem de Ação, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a modificação
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Update(){
		
		if(mantAluno.modificar(aluno)){
			addActionMessage("Aluno modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a modificação");
		return ERROR;
	}
	
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remoção do aluno:
	 * 	-caso for possível realizar a remoção
	 * 		-Adicona uma mensagem de Ação, limpa o model aluno e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a remoção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Delete(){
		
		if(mantAluno.remover(aluno)){
			addActionMessage("Aluno removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a remoção");
		return ERROR;
	}
	//limpa os campos do model e também do atributo confirmação
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
