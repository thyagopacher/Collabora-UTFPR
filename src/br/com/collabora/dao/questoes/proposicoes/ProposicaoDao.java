package br.com.collabora.dao.questoes.proposicoes;

import java.util.List;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Proposicao;

@Local
public interface ProposicaoDao extends DaoGenerico<Proposicao> {

	List<Proposicao> listarPorIds(List<String> idsProposicoes);

}
