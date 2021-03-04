package businessService.CRUDAprendizagem.pesquisarQuestao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelObjects.Conteudo;
import modelObjects.Proposicao;
import modelObjects.Questao;


public interface IPesqQuestao {
	
	public Questao pesquisar(int id);
	
	//public Questao pesquisar(Questao questao);
	
	public List<Questao> pesquisar(Conteudo con);
	
	public Questao transformar(ResultSet rs);
	
//	public Referencia pesquisarReferencia(Questao questao);
//	
//	public Referencia pesquisarReferencia(Referencia referencia);
	
	public ArrayList<Proposicao> pesquisarProposicoes (Questao questao);

}
