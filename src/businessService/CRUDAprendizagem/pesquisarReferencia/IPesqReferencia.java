package businessService.CRUDAprendizagem.pesquisarReferencia;

import java.util.List;

import modelObjects.Questao;
import modelObjects.Referencia;

public interface IPesqReferencia {
	
	public boolean verificarExistencia(Referencia ref);
	
	public List<Referencia> pesquisarTodos();
	
	public Referencia pesquisar(Questao questao);
	
	public Referencia pesquisar(int id);
}
