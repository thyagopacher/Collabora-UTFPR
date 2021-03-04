package br.com.collabora.sessoes.alunos;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Local;
import javax.json.JsonObject;
import javax.websocket.Session;

import modelObjects.Aluno;
import modelObjects.Grupo;

@Local
public interface ControladorSessoesAlunos extends Serializable {

	boolean estaAtiva(String id);

	boolean estaAtivaETemGrupoEAluno(String id);

	void removerSessao(String id);

	void atualizarSessao(String id, Optional<Aluno> aluno, Optional<Grupo> grupo);

	void iniciarSessaoWebSocket(String id, Session sessao);

	void finalizarSessaoWebSocket(Session sessao);

	Optional<Grupo> obterGrupo(String id);

	Optional<Aluno> obterAluno(String id);

	long obterNumDeAlunosLogados(Grupo g);
	
	Set<Aluno> obterAlunosLogados(Grupo g);

	void enviarMensagemAIntegrantes(Optional<Grupo> grupo, JsonObject mensagem);

}
