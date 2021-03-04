package action.login;


import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarAlunosPorTurma.IPesquisarAlunosPorTurma;
import businessService.CRUDAprendizagem.psequisarConjuntoAluno.IPesquisarConjuntoAluno;
import businessService.login.pesquisarAluno.IPesquisarAluno;
import modelObjects.Aluno;
import modelObjects.Turma;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de pesquisar Aluno (IPesquisarALuno)
 *  - Que oferece o serviço de pesquisar um conjunto de Alunos (IPesquisarConjuntoAluno)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */
public class ActionPesquisarAluno extends ActionSupport implements IPesquisarAluno, IPesquisarConjuntoAluno, IPesquisarAlunosPorTurma{
	
	/* 	records é o nome default utilizado pelo componente DataTable da Visão,
	 * result é a validação padrão também para o DataTable.
	 * 	-	result pode receber 2 valores: OK ou false, onde:
	 * 		-OK : resultado pode ser utilizado pelo DataTable
	 * 		-false: resultado deve ser descartado pelo DataTable
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Aluno> records;
	private String pesquisaNome;
	private String result;
	private Turma turma;
	private String idTurma;


	/**
	 * 		pesquisarNome
	 * 
	 * 	coloca na variável record o alunos que possui o nome que esta contido em pesquisaNome
	 * 
	 * @return SUCCESS se a pesquisa possuir mais de um elementos
	 * 			ERROR se a pesquisa não possuir elementos
	 */
	public String pesquisarNome(){
		
		this.records = pesqAluno.pesquisarNome(pesquisaNome);
		
		if(records.isEmpty()){
			result= "false";
			return ERROR;
		}
		else {
			result = "OK";
			return SUCCESS;
		}
		
	}
	
	public String pesquisarTurma(){
		
		if(this.idTurma.isEmpty())return ERROR;
		turma = new Turma();
		turma.setId(Integer.parseInt(this.idTurma));
		
		this.records = pesqAlunosPorTurma.pequisarPorTurma(turma);
		
		if(records.isEmpty()){
			result= "false";
			return ERROR;
		}
		else {
			result = "OK";
			return SUCCESS;
		}
		
	}
	
	/*
	 * 			Pesquisar Todos
	 * 
	 * 	coloca em records todos as tuplas referentes aos alunos da base de dados.
	 * 
	 * retorna ERROR se records for vazio ou SUCCES se record contem mais de um elemento
	 * 
	 * 
	 * */
	public String pesquisarTodos(){
		
		this.records = pesqConjAlu.pesquisarTodos();
		
		
		if(records.isEmpty()) {
			result= "false";
			return ERROR;
		}
		else{
			result = "OK";
			return SUCCESS;
		}
		
	}

	public List<Aluno> getRecords() {
		return records;
	}

	public void setRecords(List<Aluno> alunos) {
		this.records = alunos;
	}

	public String getPesquisaNome() {
		return pesquisaNome;
	}

	public void setPesquisaNome(String pesquisaNome) {
		this.pesquisaNome = pesquisaNome;
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
