package action.CRUDAprendizagem;

import java.util.Calendar;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterReferencia.IManterReferencia;
import modelObjects.Referencia;

public class ActionManterReferencia extends ActionSupport implements IManterReferencia, ModelDriven<Referencia>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Referencia referencia = new Referencia();
	private String button = new String();
	private String ano = new String();
	

	@Override
	public Referencia getModel() {
		return referencia;
	}
	
	@Override
	public void validate(){
		
		//única verificação para excluir é possuir o id
				if("Excluir".equals(getButton())){
					
					if( referencia.getIdReferencia() == null || referencia.getIdReferencia().equals(0))
						addActionMessage("Você deve selecionar a referência para poder exclui-la!");	
					
					return;
				}
				
				if(referencia.getAutor() == null || referencia.getAutor().isEmpty()){
					addFieldError("referencia.autor", "O campo autor não pode ser vazio!");
					return;
				}
				
				if(ano == null || ano.isEmpty()){
					addFieldError("ano", "O ano autor não pode ser vazio!");
					return;
				}else{
					referencia.setData(Calendar.getInstance());
					referencia.getData().set(Integer.parseInt(ano), 1, 1);
				}
				
				if(referencia.getEditora().equals(null) || 
						referencia.getEditora().isEmpty()){
					addFieldError("referencia.editora", "O campo editora não pode ser vazio!");
				}
				
				if(referencia.getEditora().equals(null) || 
						referencia.getEditora().isEmpty()){
					addFieldError("referencia.editora", "O campo editora não pode ser vazio!");
				}
				
				if(referencia.getTitulo().equals(null) || 
						referencia.getTitulo().isEmpty()){
					addFieldError("referencia.titulo", "O campo título não pode ser vazio!");
				}
		
	}
	@Override
	public String execute(){
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(referencia.getIdReferencia() == null || 
				referencia.getIdReferencia().equals(0)){
			return this.Insert();
		}
		else{
			return this.Update();
			
		}
	}
	
	private String Update() {
		if(mantRef.Update(referencia)){
			addActionMessage("Referência modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a modificação");
		return ERROR;
	}

	private String Insert() {
		if(mantRef.Insert(referencia)){
			addActionMessage("Referência inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a inserção");
		return ERROR;
	}

	private String Delete() {		
		if(mantRef.Delete(referencia)){
			addActionMessage("Referência removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a remoção");
		return ERROR;
	}

	private void Clean() {
		this.button="";
		this.ano="";
		this.referencia.setAutor("");
		this.referencia.setEditora("");
		this.referencia.setTitulo("");
		this.referencia.setIdReferencia(0);
		this.referencia.setData(null);
	}

	public Referencia getReferencia() {
		return referencia;
	}

	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

}
