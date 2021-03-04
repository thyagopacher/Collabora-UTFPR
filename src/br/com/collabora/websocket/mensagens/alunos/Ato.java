package br.com.collabora.websocket.mensagens.alunos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import br.com.collabora.sessoes.alunos.ControladorSessoesAlunos;
import br.com.collabora.sessoes.alunos.chat.ControladorChats;

public enum Ato {
	INICIAR {
		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {
			controladorSessoes.get().iniciarSessaoWebSocket(mensagem.getString(TAG_ID_SESSAO_ALUNO), sessao);
		}
	},
	SOLICITAR_INFO_INICIAIS {

		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {
			controladorChats.get().solicitarInfoIniciais(mensagem.getString(TAG_ID_SESSAO_ALUNO));
		}

	},
	ENVIAR_COLABORACAO_TEXTO {
		private final String TAG_COLABORACAO_MSG = "msg";

		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {
			controladorChats.get().incluirColaboracaoTextual(mensagem.getString(TAG_ID_SESSAO_ALUNO),
					mensagem.getString(TAG_COLABORACAO_MSG));
		}
	},
	RESPONDER_QUESTAO {
		private final String TAG_PROPOSICOES_MSG = "proposicoes";

		private final Function<String, String> idProposicaoParser = id -> id.replace("prop", "").replace("\"", "");
		private final Predicate<String> idProposicaoFilter = id -> id.contains("prop");

		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {

			controladorChats.get().responderQuestao(mensagem.getString(TAG_ID_SESSAO_ALUNO),
					getListaIdsPreposicoes(mensagem));
		}

		private List<String> getListaIdsPreposicoes(JsonObject mensagem) {
			final List<String> idsProposicoesSelecionadas = new ArrayList<>();

			final JsonArray arrayIdsPreposicoes = mensagem.getJsonArray(TAG_PROPOSICOES_MSG);

			arrayIdsPreposicoes.stream().filter(v -> v.getValueType().equals(ValueType.STRING)).map(JsonValue::toString)
					.filter(idProposicaoFilter).map(idProposicaoParser::apply).forEach(idsProposicoesSelecionadas::add);

			return idsProposicoesSelecionadas;
		}
	},
	FINALIZAR_QUESTAO {
		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {
			controladorChats.get().finalizarQuestao(mensagem.getString(TAG_ID_SESSAO_ALUNO));
		}
	},
	FINALIZAR_ATIVIDADE {

		@Override
		public void iniciar(Session sessao, JsonObject mensagem, Supplier<ControladorSessoesAlunos> controladorSessoes,
				Supplier<ControladorChats> controladorChats) {
			controladorChats.get().finalizarAtividade(mensagem.getString(TAG_ID_SESSAO_ALUNO));
		}

	};

	protected final String TAG_ID_SESSAO_ALUNO = "idSessaoAluno";

	public abstract void iniciar(Session sessao, JsonObject mensagem,
			Supplier<ControladorSessoesAlunos> controladorSessoes, Supplier<ControladorChats> controladorChats);

	public static Ato obterPorAcao(JsonObject msg) {
		return Stream.of(Ato.values())
				.filter(m -> m.name().toLowerCase().equals(msg.getString(Ato.class.getSimpleName().toLowerCase())))
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}

}
