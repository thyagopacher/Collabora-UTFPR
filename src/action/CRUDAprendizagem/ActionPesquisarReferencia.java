package action.CRUDAprendizagem;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarReferencia.IPesquisarReferencia;
import modelObjects.Questao;
import modelObjects.Referencia;

public class ActionPesquisarReferencia extends ActionSupport implements IPesquisarReferencia{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Referencia> records;
	private String result;
	private String idQuestao = new String();
	
	@Override
	public String execute(){
		
		records = pesqRef.pesquisarTodos();
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("N�o foi poss�vel pesquisar as Ementas!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
		
	}
	
	public String pesquisarPorQuestao(){
		
		if (idQuestao == null || idQuestao.isEmpty()){
			addActionError("� necess�rio uma quest�o");
			return SUCCESS;
		}
		
		Questao questao = new Questao();
		questao.setIdQuestao(Integer.parseInt(idQuestao));
		
		Referencia ref = pesqRef.pesquisar(questao);
		
		records = new ArrayList<>();
		records.add(ref);
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("N�o foi poss�vel pesquisar a Refer�ncia dessa quest�o!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
		
	}

	public List<Referencia> getRecords() {
		return records;
	}

	public void setRecords(List<Referencia> records) {
		this.records = records;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIdQuestao() {
		return idQuestao;
	}

	public void setIdQuestao(String idQuestao) {
		this.idQuestao = idQuestao;
	}
}
