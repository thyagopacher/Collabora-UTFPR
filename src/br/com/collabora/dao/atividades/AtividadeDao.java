package br.com.collabora.dao.atividades;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import br.com.collabora.modelo.dtos.atividades.AtividadeDTO;
import modelObjects.Atividade;
import modelObjects.Grupo;
import modelObjects.Questao;

@Local
public interface AtividadeDao extends DaoGenerico<Atividade> {

	Optional<Questao> obterProximaQuestaoParaExecucao(Grupo grupo, Atividade atividade);

	List<AtividadeDTO> listarDTOsAtividadePendentesDoGrupo(Grupo grupo);
	
	List<Atividade> listarAtividadesPendentesDoGrupo(Grupo grupo);

	List<Atividade> listarAtividadesFinalizadasDoGrupo(Grupo grupo);

	List<AtividadeDTO> listarDTOsAtividadesFinalizadasDoGrupo(Grupo grupo);

}
