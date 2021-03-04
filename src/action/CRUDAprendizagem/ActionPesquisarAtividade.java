package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarAtividade.IPesquisarAtividade;
import modelObjects.Atividade;
/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de pesquisar um conjunto de Conteudos (IPesquisarConjuntoConteudo)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionPesquisarAtividade extends ActionSupport implements IPesquisarAtividade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Atividade> records;
	private String result;
	
	/*	
	 * 		Execute
	 * 
	 * 	tenta pesquisa todos as Ementas e verifica se foi poss�vel pesquisa-las ou n�o
	 * 
	 * 	Retorna ERROR se o resultado da pesquisa for vazio
	 * 	Retorna SUCCESS se o resultado da pesquisa n�o for vazio
	 * 
	 * */
	@Override
	public String execute(){
		
		records = pesqAtividade.pesquisarAll();
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("N�o foi poss�vel pesquisar as Ementas!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
	}
	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public List<Atividade> getRecords() {
		return records;
	}


	public void setRecords(List<Atividade> records) {
		this.records = records;
	}
	

}
