package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarConjuntoConteudo.IPesquisarConjuntoConteudo;
import modelObjects.Conteudo;
import modelObjects.Ementa;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o serviço de pesquisar um conjunto de Conteudos (IPesquisarConjuntoConteudo)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de ações no struts2
 * */
public class ActionPesquisarConteudo extends ActionSupport implements IPesquisarConjuntoConteudo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Conteudo> records;
	private String result;
	private String idEmenta = new String();
	
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
		
		records = pesqConjConteudo.pesquisarTodos();
		
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
	 * 		Pesquisar por Ementa
	 * 
	 * 	pesuisa todos os conteudos dada uma ementa
	 * 
	 * */
	public String pesquisarPorEmenta(){
		
		
		Ementa ementa = new Ementa();
		ementa.setIdEmenta(Integer.parseInt(idEmenta));
		
		records = pesqConjConteudo.pesquisarPorEmenta(ementa);
		
		if(records.isEmpty()) {
			result= "false";
			addActionError("Não foi possível pesquisar as ementas desta disciplina!");
		}
		else{
			result = "OK";
		}
		
		return SUCCESS;
	}

	public List<Conteudo> getRecords() {
		return records;
	}

	public void setRecords(List<Conteudo> records) {
		this.records = records;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIdEmenta() {
		return idEmenta;
	}

	public void setIdEmenta(String idEmenta) {
		this.idEmenta = idEmenta;
	}
	

}
