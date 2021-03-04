package action.CRUDAprendizagem;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterDisciplina.IManterDisciplina;
import modelObjects.Disciplina;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de CRUD para a disciplina (IManterDisciplina)
 * 	- Que oferece o seri�o interface com a vis�o (ModelDriven)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionManterDisciplina extends ActionSupport implements ModelDriven<Disciplina>, IManterDisciplina{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Disciplina disciplina = new Disciplina();
	private String button;


	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	
	@Override
	public void validate(){
		
		//�nica verifica��o para excluir � possuir o id
		if("Excluir".equals(getButton())){
			
			if( disciplina.getIdDisicplina() == null)
				addActionMessage("Voc� deve selecionar a disciplina para poder exclui-la!");	
			
			return;
		}
		
		if(disciplina.getNome() == null || disciplina.getNome().isEmpty()){
			addFieldError("nome", "O campo nome n�o pode ser vazio!");
			return;
		}
		
		if(disciplina.getCodigo() == null || disciplina.getCodigo().isEmpty()){
			addFieldError("codigo", "O campo codigo n�o pode ser vazio!");
		}
	}
	
	@Override
	public String execute(){
		
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(disciplina.getIdDisicplina() == null || disciplina.getIdDisicplina().equals(0)){
			return this.Insert();
		}
		else{
			return this.Update();
			
		}
	}
	
	private String Delete() {
		if(mantDisciplina.remover(disciplina)){
			addActionMessage("Disciplina removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}

	public String Insert(){
		
		if(mantDisciplina.inserir(disciplina)){
			addActionMessage("Disciplina inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
		
	}
	
	
	public String Update(){
		
		if(mantDisciplina.modificar(disciplina)){
			addActionMessage("Disciplina modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
		
	}
	private void Clean(){
		
		disciplina.setCodigo("");
		disciplina.setNome("");
		disciplina.setIdDisicplina(0);
		
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public Disciplina getModel() {
		return disciplina;
	}

}
