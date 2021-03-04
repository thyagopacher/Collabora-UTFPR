package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarGrupo.IPesquisarGrupo;
import modelObjects.Grupo;
import modelObjects.Turma;

public class ActionPesquisarGrupo extends ActionSupport implements IPesquisarGrupo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Grupo> records;
	private String result;
	private String idTurma = new String();
	
	public String pesquisarTurma(){
		
		if(idTurma.isEmpty() || idTurma.equals(null)){
			addActionError("É necessário uma turma e atividade para realizar a pesquisa!");
			return SUCCESS;
		}
		
		Turma turma = new Turma();
		turma.setId(Integer.parseInt(idTurma));
		
		records = pesqGrupo.PesquisarByTurmaEAtividade(turma);
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar!");
			return SUCCESS;
		}
		else{
			result = "OK";
			return SUCCESS;
		}
		
	}
	
	
	
	
	public List<Grupo> getRecords() {
		return records;
	}
	public void setRecords(List<Grupo> records) {
		this.records = records;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}


	public String getIdTurma() {
		return idTurma;
	}


	public void setIdTurma(String idTurma) {
		this.idTurma = idTurma;
	}

}
