package br.com.collabora.sessoes.alunos.chat;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import br.com.collabora.modelo.sessao.chat.inicio.RequisicaoInicioAtividade;
import br.com.collabora.modelo.sessao.chat.inicio.RespostaInicioAtividade;

@Local
public interface ControladorChats extends Serializable, AtividadeObserver {

	RespostaInicioAtividade iniciarAtividade(RequisicaoInicioAtividade req);

	void incluirColaboracaoTextual(String idSessao, String mensagem);

	void incluirColaboracaoPorArquivo(String idSessao, File file, String contentType, String filename);

	void responderQuestao(String idSessao, List<String> idsProposicoes);

	void finalizarQuestao(String idSessao);

	void solicitarInfoIniciais(String idSessao);

	void finalizarAtividade(String idSessao);

}
