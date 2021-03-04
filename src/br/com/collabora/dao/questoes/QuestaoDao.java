package br.com.collabora.dao.questoes;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Atividade;
import modelObjects.Questao;

@Local
public interface QuestaoDao extends DaoGenerico<Questao> {

	Optional<Questao> obterPrimeiraQuestaoExceto(Atividade atividade, List<String> idsQuestoes);

	Integer getNumQuestoesAtividade(Atividade ati);

}
