package br.com.collabora.dao.execucoes.questoes;

import java.time.Duration;
import java.util.List;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Atividade;
import modelObjects.ExecucaoQuestao;
import modelObjects.FeedbackAtividade;
import modelObjects.FeedbackColaboracao;
import modelObjects.Grupo;
import modelObjects.Pontuacao;
import modelObjects.Questao;
import modelObjects.StatusQuestao;

@Local
public interface ExecucaoQuestaoDao extends DaoGenerico<ExecucaoQuestao> {

	boolean finalizarExecucao(String id);

	List<Duration> buscarPeriodosJaExecutados(Grupo grupo, Questao questao);

	List<String> listarIdsDeAtividadesFinalizadasDoGrupo(Grupo grupo);

	List<String> listarIdsDeQuestoesFinalizadasDoGrupoEAtividade(Grupo grupo, Atividade atividade);

	List<String> listarIdsDeQuestoesSemExecucaoDoGrupoEAtividade(Grupo grupo, Atividade atividade);
	
	boolean indefinir(String id);

	StatusQuestao atualizarRespostasEMudarStatus(ExecucaoQuestao execucaoQuestao);
	
	List<Pontuacao> listarPontuacoesDeAtividade(Grupo grupo, Atividade  atividade);

	List<FeedbackColaboracao> listarFeedbacksColaboracao(Grupo grupo, Atividade atividade);

	FeedbackAtividade obterFeedbackAtividade(Grupo grupo, Atividade atividade);

}
