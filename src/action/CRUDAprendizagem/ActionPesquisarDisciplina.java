package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import modelObjects.Disciplina;


public class ActionPesquisarDisciplina extends ActionSupport implements IPesquisarDisciplina{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Disciplina> records;
	private String result;
	
	public String pesquisarTodos(){
		
		records = pesqDisc.pesquisaTodos();
		
		if(records == null){
			result = "falue";
			return ERROR;
		}
		else{
			result = "OK";
			return SUCCESS;
		}
		
		
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Disciplina> getRecords() {
		return records;
	}

	public void setRecords(List<Disciplina> records) {
		this.records = records;
	}

}
