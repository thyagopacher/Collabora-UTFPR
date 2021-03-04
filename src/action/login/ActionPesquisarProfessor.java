package action.login;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import businessService.login.pesquisarProfessor.IPesquisarProfessor;
import modelObjects.Professor;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de pesquisar Professor (IPesquisarProfessor)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */

public class ActionPesquisarProfessor 
	extends ActionSupport 
	implements IPesquisarProfessor {
	
	/* 	records é o nome default utilizado pelo componente DataTable da Visão,
	 * result é a validação padrão também para o DataTable.
	 * 	-	result pode receber 2 valores: OK ou false, onde:
	 * 		-OK : resultado pode ser utilizado pelo DataTable
	 * 		-false: resultado deve ser descartado pelo DataTable
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Professor> records = new ArrayList<>();
	public ArrayList<Professor> getRecords() {
		return records;
	}


	public void setRecords(ArrayList<Professor> records) {
		this.records = records;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	private String result;
	
	 /*
		 * 			Pesquisar Todos
		 * 
		 * 	coloca em records todos as tuplas referentes aos alunos da base de dados.
		 * 
		 * retorna ERROR se records for vazio ou SUCCES se record contem mais de um elemento
		 * 
		 * 
		 * */
	public String buscarTodos(){
		
		this.records = pesqProf.pequisarTodos();
		
		if(records.isEmpty()){
			result= "false";
			return ERROR;
		}
		else {
			result = "OK";
			return SUCCESS;
		}
		
	}	
	

}
