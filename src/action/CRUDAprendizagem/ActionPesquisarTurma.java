package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarTurma.IPesquisarTurma;
import modelObjects.Disciplina;
import modelObjects.Turma;
/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de pesquisar Turma (IPesquisarTurma)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */
public class ActionPesquisarTurma extends ActionSupport implements IPesquisarTurma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Turma> records;
	private String result;
	private String idDisciplina = new String();
	
	
	/*	
	 * 		Execute
	 * 
	 * 	tenta pesquisa todos as turmas e verifica se foi possível pesquisa-las ou não
	 * 
	 * 	Retorna ERROR se o resultado da pesquisa for vazio
	 * 	Retorna SUCCESS se o resultado da pesquisa não for vazio
	 * 
	 * */
	@Override
	public String execute(){
		
		records = pesqTurma.pesquisarTodas();
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar as turmas!");
			return ERROR;
		}
		else{
			result = "OK";
			return SUCCESS;
		}
	}
	
	public String pesqByDisciplina(){
		
		System.out.println(idDisciplina);
		
		Disciplina disciplina = new Disciplina();
		disciplina.setIdDisicplina(Integer.parseInt(idDisciplina));
		
		records = pesqTurma.pesquisarByDisciplina(disciplina);
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar as turmas!");
			return SUCCESS;
		}
		else{
			result = "OK";
			return SUCCESS;
		}	
		
		
	}
	
	public List<Turma> getRecords() {
		return records;
	}
	public void setRecords(List<Turma> records) {
		this.records = records;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(String idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

}
