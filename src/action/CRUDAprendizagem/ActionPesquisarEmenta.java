package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarConjuntoEmenta.IPesquisarConjuntoEmenta;
import modelObjects.Disciplina;
import modelObjects.Ementa;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de pesquisar um conjunto de Ementa (IPesquisarConjuntoEmenta)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */
public class ActionPesquisarEmenta extends ActionSupport implements IPesquisarConjuntoEmenta{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Ementa> records;
	private String result;
	private String idDisciplina = new String();
	
	
	/*	
	 * 		Execute
	 * 
	 * 	tenta pesquisa todos as Ementas e verifica se foi possível pesquisa-las ou não
	 * 
	 * 	Retorna ERROR se o resultado da pesquisa for vazio
	 * 	Retorna SUCCESS se o resultado da pesquisa não for vazio
	 * 
	 * */
	@Override
	public String execute(){
		
		records = pesqConjEmenta.pesquisarTodos();
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar as Ementas!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
	}
	
	
	/*	
	 * 		pesquisar por disciplina
	 * 
	 * 	pesquisa as ementas da disciplina que possui o id igual ao campo iddisciplina
	 * 
	 * 	Retorna SUCCESS 
	 * 
	 * */
	public String pesquisarPorDisciplina(){
		
		
		Disciplina disciplina = new Disciplina();
		disciplina.setIdDisicplina(Integer.parseInt(idDisciplina));
		
		records = pesqConjEmenta.pesquisar(disciplina);
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar as ementas desta disciplina!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
	}
	
	public List<Ementa> getRecords() {
		return records;
	}
	public void setRecords(List<Ementa> records) {
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
