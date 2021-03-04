package businessService.CRUDAprendizagem.pesquisarConjuntoConteudo;

import java.util.ArrayList;
import java.util.List;

import modelObjects.Conteudo;
import modelObjects.Ementa;



public interface IPesqConjConteudo {
	
	public boolean pesquisar (ArrayList<Conteudo> conteudos);
	
	public List<Conteudo> pesquisarPorEmenta(Ementa ementa);
	
	public List<Conteudo> pesquisarTodos();

}
