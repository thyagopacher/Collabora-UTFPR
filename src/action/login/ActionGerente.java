package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterGerente.IGerenciarGerente;
import modelObjects.Gerente;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferecem o serviço de CRUD para o gerente (IGerenciarGerente)
 * 	- Que oferecem o seriço interface com a visão (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */
public class ActionGerente extends ActionSupport implements ModelDriven<Gerente>, IGerenciarGerente{
	
	private static final long serialVersionUID = 1L;
	
	private Gerente gerente = new Gerente();
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
		
		//verificações para a inserção, modificação e exclusão dos dados
		
		//única verificação para excluir é possuir o id
		if("Excluir".equals(getButton())){
			
			if( gerente.getId() == null)
				addActionError("Você deve selecionar o gerente para poder exclui-lo!");	
			
			return;
		}
		
		if(gerente.getNome().isEmpty()){
			addFieldError("gerente.nome", "O campo nome não pode ser deixado em branco. Verifique!");
			System.out.println("gerente.getnome");
			return;
		}
				
		
		if(gerente.getSenha().isEmpty()){
			addFieldError("gerente.senha","O campo senha não pode ser branco!");
			return;
		}
				
    }
	
	/*
	 * 		Execute(Método Original da classe ActionSupport)
	 * 
	 * Método default do mapeamento do struts2.
	 * 
	 * Realiza verificações quanto ao qual ação o usuário deseja executar
	 * (inclusão, exclusão ou inserção) e chama o método responsável pelo mesmo
	 * 
	 * Adiociona uma mensagem de erro ao campo confirmação caso as senhas
	 * forem diferentes e retorna erro.
	 * 
	 * 
	 * */
	@Override
	public String execute() throws Exception {
		
		//System.out.println("execute");
		
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		
		if(gerente.getSenha().equals(confirmacao)){
			
			if(gerente.getId() == null || gerente.getId().equals(0)){
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
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modificação do gerente:
	 * 	-caso for possível realizar a modificação
	 * 		-Adicona uma mensagem de Ação, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a modificação
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	private String Update() {
		
		if(g.modificar(gerente)){
			addActionMessage("Gerente modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a modificação");
		return ERROR;
	}

	
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remoção do gerente:
	 * 	-caso for possível realizar a remoção
	 * 		-Adicona uma mensagem de Ação, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a remoção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	private String Delete() {
		
		if(g.remover(gerente)){
			addActionMessage("Gerente removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a remoção");
		return ERROR;

	}

	
	/*
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inserção do gerente:
	 * 	-caso for possível realizar a inserção
	 * 		-Adicona uma mensagem de Ação, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso não for possível realizar a inserção
	 * 		-Adicona uma mensagem de erro de Ação e retorna ERROR
	 * */
	public String Insert(){
		
		if(g.inserir(this.gerente)){
			addActionMessage("Gererente salvo com sucesso!");
			Clean();
			return SUCCESS;
		}
		else{
			addActionError("Não foi possível inserir o Gerente");
			return ERROR;
		}
		
	}
	
	//limpa os campos do model e também do atributo confirmação
	private void Clean(){
		getGerente().setNome("");
		getGerente().setSenha("");
		confirmacao = "";
		getGerente().setId(0);
	}
	
	
	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}
	
	

	@Override
	public Gerente getModel() {
		return this.gerente;
	}
	
	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

}
