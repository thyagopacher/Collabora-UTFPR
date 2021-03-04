package action.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.login.manterGerente.IGerenciarGerente;
import modelObjects.Gerente;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferecem o servi�o de CRUD para o gerente (IGerenciarGerente)
 * 	- Que oferecem o seri�o interface com a vis�o (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionGerente extends ActionSupport implements ModelDriven<Gerente>, IGerenciarGerente{
	
	private static final long serialVersionUID = 1L;
	
	private Gerente gerente = new Gerente();
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
		
		//verifica��es para a inser��o, modifica��o e exclus�o dos dados
		
		//�nica verifica��o para excluir � possuir o id
		if("Excluir".equals(getButton())){
			
			if( gerente.getId() == null)
				addActionError("Voc� deve selecionar o gerente para poder exclui-lo!");	
			
			return;
		}
		
		if(gerente.getNome().isEmpty()){
			addFieldError("gerente.nome", "O campo nome n�o pode ser deixado em branco. Verifique!");
			System.out.println("gerente.getnome");
			return;
		}
				
		
		if(gerente.getSenha().isEmpty()){
			addFieldError("gerente.senha","O campo senha n�o pode ser branco!");
			return;
		}
				
    }
	
	/*
	 * 		Execute(M�todo Original da classe ActionSupport)
	 * 
	 * M�todo default do mapeamento do struts2.
	 * 
	 * Realiza verifica��es quanto ao qual a��o o usu�rio deseja executar
	 * (inclus�o, exclus�o ou inser��o) e chama o m�todo respons�vel pelo mesmo
	 * 
	 * Adiociona uma mensagem de erro ao campo confirma��o caso as senhas
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
	 * 		Tenta realizar a modifica��o do gerente:
	 * 	-caso for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de A��o, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Update() {
		
		if(g.modificar(gerente)){
			addActionMessage("Gerente modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remo��o do gerente:
	 * 	-caso for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de A��o, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Delete() {
		
		if(g.remover(gerente)){
			addActionMessage("Gerente removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;

	}

	
	/*
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inser��o do gerente:
	 * 	-caso for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de A��o, limpa o model Gerente e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	public String Insert(){
		
		if(g.inserir(this.gerente)){
			addActionMessage("Gererente salvo com sucesso!");
			Clean();
			return SUCCESS;
		}
		else{
			addActionError("N�o foi poss�vel inserir o Gerente");
			return ERROR;
		}
		
	}
	
	//limpa os campos do model e tamb�m do atributo confirma��o
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
