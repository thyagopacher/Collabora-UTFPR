package br.com.collabora.sessoes.alunos.chat;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import modelObjects.Atividade;
import modelObjects.Grupo;

@Local
public interface ControladorAtividade extends TemporizadorObserver {

	public String getId();

	public boolean mesmoGrupo(Grupo grupo);

	public boolean temAtividade();

	public Optional<Atividade> getAtividade();

	public void inicializar(Grupo grupo, Atividade atividade);
	
	public void notificarInfoIniciais(String idSessao);

	public void incluirColaboracaoTextual(String idSessao, String mensagem);

	public void incluirColaboracaoPorArquivo(String idSessao, File file, String contentType, String fileName);

	public void incluirObservador(AtividadeObserver observador);

	public void responderQuestao(String idSessao, List<String> idsProposicoes);

	public void finalizarQuestao();

	public void finalizarAtividade();

}
