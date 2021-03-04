package businessService.CRUDAprendizagem.pesquisarAtividade;

import java.util.List;

import modelObjects.Aluno;
import modelObjects.Atividade;

public interface IpesqAtividade {

	public Atividade pesquisar(int id);

	public Atividade pesquisar(Atividade atividade);

	public List<Atividade> pesquisarAbertasComAluno(Aluno aluno);

	public List<Atividade> pesquisarHistoricoDeAluno(Aluno aluno);
}
