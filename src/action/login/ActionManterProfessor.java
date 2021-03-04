package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterProfessor.IManterProfessor;
import modelObjects.Professor;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de CRUD para o professor (IManterProfessor)
 * 	- Que oferece o seriço interface com a visão (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
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
			
			if( professor.getId() == null)
				addActionMessage("Você deve selecionar o professor para poder exclui-lo!");	
			
			return;
		}
		
		//demais verificações para a inserção e modificação dos dados
		if(getProfessor().getNome().isEmpty()) {
			addFieldError("professor.nome", "O campo nome não pode ser vazio");
			return;
		}
		
		if(getProfessor().getRegistro().isEmpty()){
			addFieldError("professor.registro", "O campo registro não pode ser vazio");
			return;
		}
		
		if(getProfessor().getSenha().isEmpty()){
			addFieldError("professor.senha", "O campo senha não pode ser vazio");
			return;
		}
		
		if(confirmacao.isEmpty())
			addFieldError("confirmacao", "O campo confirmacao não pode ser vazio");
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
	 * 		Tenta realizar a remoção do professor:
	 * 	-caso for possível realizar a remoção
	 * 		-Adicona uma mensagem de Ação, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a remoção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	private String Delete() {
		
		if(mantProfessor.remover(professor)){
			addActionMessage("Professor removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a remoção");
		return ERROR; 
	}
	
	/*
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inserção do professor:
	 * 	-caso for possível realizar a inserção
	 * 		-Adicona uma mensagem de Ação, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a inserção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Insert(){
		
		if(mantProfessor.inserir(professor)){
			addActionMessage("Professor inserido com sucesso");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível inserir este professor, verifique!");
		return ERROR;
		
		
	}
	
	/*
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modificação do professor:
	 * 	-caso for possível realizar a modificação
	 * 		-Adicona uma mensagem de Ação, limpa o model professor e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a modificação
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Update(){
		
		if(mantProfessor.modificar(professor)){
			addActionMessage("Professor modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a modificação");
		return ERROR;
		
	}
	
	//limpa os campos do model e também do atributo confirmação
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
