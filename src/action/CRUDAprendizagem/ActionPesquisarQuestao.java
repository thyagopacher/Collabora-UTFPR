package action.CRUDAprendizagem;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import businessService.CRUDAprendizagem.pesquisarQuestao.IPesquisarQuestao;
import modelObjects.Conteudo;
import modelObjects.Disciplina;
import modelObjects.Ementa;
import modelObjects.Questao;

public class ActionPesquisarQuestao extends ActionSupport implements IPesquisarQuestao {
	
	private static final long serialVersionUID = 1L;
	private List<Questao> records;
	private String result;
	private String idEmenta = new String();
	private String idConteudo = new String();
	private String idDisciplina = new String();
	
	public String pesquisarPorConteudo(){
		
		System.out.println("idementa: "+idEmenta);
		System.out.println("idconteudo: "+idConteudo);
		System.out.println("ididisci: "+idDisciplina);
		
		Conteudo con = new Conteudo();
		con.setIdConteudo(Integer.parseInt(idConteudo));
		con.setEmenta(new Ementa());
		con.getEmenta().setIdEmenta(Integer.parseInt(idEmenta));
		con.getEmenta().setDisciplina(new Disciplina());
		con.getEmenta().getDisciplina().setIdDisicplina(Integer.parseInt(idDisciplina));
		
		records = pesqQuestao.pesquisar(con);
		
		if(records.isEmpty()){
			result = "";
			addActionMessage("Não foi possível encontrar quesões");
			return SUCCESS;
		}else{
			result="OK";
			return SUCCESS;
		}
		
	}
	
	public List<Questao> getRecords() {
		return records;
	}
	public void setRecords(List<Questao> records) {
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
	public String getIdConteudo() {
		return idConteudo;
	}
	public void setIdConteudo(String idConteudo) {
		this.idConteudo = idConteudo;
	}
	public String getIdDisciplina() {
		return idDisciplina;
	}
	public void setIdDisciplina(String idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

}
