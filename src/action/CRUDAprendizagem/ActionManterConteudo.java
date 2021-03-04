package action.CRUDAprendizagem;

import java.io.File;



import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterConteudo.IManterConteudo;
import modelObjects.Conteudo;
import modelObjects.Disciplina;
import modelObjects.Ementa;

public class ActionManterConteudo extends ActionSupport
implements IManterConteudo, ModelDriven<Conteudo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Conteudo conteudo = new Conteudo();
	private String button = new String();
	
	@Override
	public Conteudo getModel() {
		if(conteudo.getEmenta() == null)
			conteudo.setEmenta(new Ementa());
		
		if(conteudo.getEmenta().getDisciplina() == null)
			conteudo.getEmenta().setDisciplina(new Disciplina());
		
		
		return conteudo;
	}
	
	@Override
	public void validate(){
		
		//�nica verifica��o para excluir � possuir o id
			if("Excluir".equals(getButton())){
				
				if( conteudo.getIdConteudo() == null || conteudo.getIdConteudo().equals(0) )
					addActionMessage("Voc� deve selecionar o conteudo para poder exclui-lo!");	
				
				return;
			}
			
			if(conteudo.getNome() == null || conteudo.getNome().isEmpty()){
				addFieldError("conteudo.nome", "O campo nome n�o pode ser vazio!");
				return;
			}
			
			if(conteudo.getEmenta().getIdEmenta().equals(0) || 
					conteudo.getEmenta().getIdEmenta().equals(null)){
				
				addFieldError("conteudo.ementa.nome", "O campo ementa n�o pode ser vazio!");
				return;
			}

			if(conteudo.getEmenta().getDisciplina().getIdDisicplina().equals(0) || 
					conteudo.getEmenta().getDisciplina().getIdDisicplina().equals(null)){
				addFieldError("conteudo.ementa.disciplina.nome", "O campo disciplina n�o pode ser vazio!");
				return;
			}
			
			if(conteudo.getDescricao().equals(null)||conteudo.getDescricao().isEmpty()){
				addFieldError("conteudo.descricao", "O campo descri��o n�o pode ser vazio!");
				return;
			}
		
	}
	@Override
	public String execute(){
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(conteudo.getIdConteudo() == null || conteudo.getIdConteudo().equals(0)){
			return this.Insert();
		}
		else{
			return this.Update();
			
		}
	}

	private String Update() {
		if(mantConteudo.modificar(conteudo)){
			addActionMessage("Conte�do modificado com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	private void Clean() {
		
		this.button = "";
		this.conteudo.setDescricao("");
		this.conteudo.setNome("");
		this.conteudo.getEmenta().setIdEmenta(0);
		this.conteudo.getEmenta().setNome("");
		this.conteudo.getEmenta().getDisciplina().setIdDisicplina(0);
		this.conteudo.getEmenta().getDisciplina().setNome("");
		
	}

	private String Insert() {
		
		if(mantConteudo.inserir(conteudo)){
			addActionMessage("Conte�do inserido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
	}

	private String Delete() {
		if(mantConteudo.remover(conteudo)){
			addActionMessage("Conte�do removido com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}

	public Conteudo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	

}
