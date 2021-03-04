package businessService.CRUDAprendizagem.pesquisarConteudo;

import java.sql.ResultSet;

import modelObjects.Conteudo;

public interface IPesqConteudo {
	
	public Conteudo pesquisar (String nome);
	
	public Conteudo pesquisar (Conteudo conteudo);
	
	public Conteudo transformar (ResultSet rs);

}
