package br.com.collabora.websocket.sessoes.alunos;

import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import br.com.collabora.sessoes.alunos.ControladorSessoesAlunos;
import br.com.collabora.sessoes.alunos.chat.ControladorChats;
import br.com.collabora.websocket.mensagens.alunos.Ato;

@Stateless
@ServerEndpoint(value = "/websocket/aluno")
public class ControladorWebSocketAluno implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ControladorSessoesAlunos controladorDeSessoesGeral;

	private @Inject ControladorChats controladorChats;

	@OnOpen
	public void abrir(Session sessao) {
	}

	@OnClose
	public void fechar(Session sessao) {
		this.controladorDeSessoesGeral.finalizarSessaoWebSocket(sessao);
	}

	@OnError
	public void onErro(Throwable erro) {
		Logger.getLogger(ControladorWebSocketAluno.class.getName()).log(Level.SEVERE, null, erro);
	}

	@OnMessage
	public void manipularMensagem(String mensagem, Session sessao) {
		try (JsonReader reader = Json.createReader(new StringReader(mensagem))) {
			final JsonObject msg = reader.readObject();

			Ato.obterPorAcao(msg).iniciar(sessao, msg, () -> controladorDeSessoesGeral, () -> controladorChats);
		}
	}

}
