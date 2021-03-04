package br.com.collabora.dao.execucoes.colaboracoes;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import br.com.collabora.modelo.dtos.atividades.ColaboracoesAtividadeDTO;
import modelObjects.Atividade;
import modelObjects.Colaboracao;
import modelObjects.Grupo;

@Local
public interface ColaboracaoDao extends DaoGenerico<Colaboracao> {

	Optional<byte[]> obterBytesArquivo(String id);

	List<ColaboracoesAtividadeDTO> listarDTOsColaboracoesAtividades(Grupo grupo, List<Atividade> atividades);

}
