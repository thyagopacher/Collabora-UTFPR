package action.CRUDAprendizagem;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterEmenta.IManterEmenta;
import modelObjects.Disciplina;
import modelObjects.Ementa;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de CRUD de ementa (IManterEmenta)
 * 	- Que oferece o seri�o interface com a vis�o (ModelDriven)	
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionManterEmenta extends ActionSupport implements IManterEmenta, ModelDriven<Ementa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ementa ementa = new Ementa();
	private String button = new String();
	

	@Override
	public Ementa getModel() {
		ementa.setDisciplina(new Disciplina());
		return ementa;
	}
	
	@Override
	public void validate(){
		
		//�nica verifica��o para excluir � possuir o id
				if("Excluir".equals(getButton())){
					
					if( ementa.getIdEmenta() == null || ementa.getIdEmenta().equals(0))
						addActionMessage("Voc� deve selecionar a ementa para poder exclui-la!");	
					
					return;
				}
				
				if(ementa.getNome() == null || ementa.getNome().isEmpty()){
					addFieldError("ementa.nome", "O campo nome n�o pode ser vazio!");
					return;
				}
				
				if(ementa.getDisciplina().getIdDisicplina().equals(null) || 
						ementa.getDisciplina().getIdDisicplina().equals(0)){
					addFieldError("ementa.disciplina.nome", "O campo disciplina n�o pode ser vazio!");
				}
		
	}
	@Override
	public String execute(){
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(ementa.getIdEmenta() == null || ementa.getIdEmenta().equals(0)){
			return this.Insert();
		}
		else{
			return this.Update();
			
		}
	}
	
	private String Update() {
		if(mantEmenta.modificar(ementa)){
			addActionMessage("Ementa modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	private String Insert() {
		if(mantEmenta.inserir(ementa)){
			addActionMessage("Ementa inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
	}

	private String Delete() {
		if(mantEmenta.remover(ementa)){
			addActionMessage("Ementa removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}

	private void Clean() {
		this.button="";
		this.ementa.setIdEmenta(0);
		this.ementa.setNome("");
		this.ementa.getDisciplina().setIdDisicplina(0);
		this.ementa.getDisciplina().setNome("");
	}

	public Ementa getEmenta() {
		return ementa;
	}


	public void setEmenta(Ementa ementa) {
		this.ementa = ementa;
	}


	public String getButton() {
		return button;
	}


	public void setButton(String button) {
		this.button = button;
	}

}
