package businessService.CRUDAprendizagem.pesquisarTurma;

import java.sql.ResultSet;
import java.util.List;

import modelObjects.Turma;

public interface IPesqTurma {
	
	public List<Turma> pesquisarTodas();
	
	public Turma pesquisar(String codigo);
	
	public Turma pesquisarPorId(String id);
	
	public Turma transformar(ResultSet rs);

}
