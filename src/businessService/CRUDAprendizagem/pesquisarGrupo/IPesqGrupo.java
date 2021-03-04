package businessService.CRUDAprendizagem.pesquisarGrupo;

import java.util.List;

import modelObjects.Atividade;
import modelObjects.Grupo;
import modelObjects.Turma;

public interface IPesqGrupo {
	
	public List<Grupo> PesquisarByTurmaEAtividade(Turma turma);
	
	public List<Grupo> PesquisarAtividade (Atividade atividade);

}
