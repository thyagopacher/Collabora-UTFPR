package modelObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import br.com.collabora.websocket.mensagens.alunos.RespostaAto;

public class FeedbackQuestao {

	private final int LETTER_A_INDEX = 97;

	private final List<Resposta> respostasCorretas = new ArrayList<>();

	private final List<Resposta> respostasSelecionadas = new ArrayList<>();

	private final Pontuacao pontuacao;

	public FeedbackQuestao(Questao questao, ExecucaoQuestao exec) {

		final Comparator<Proposicao> comparatorPreposicoes = (p1, p2) -> p1.getId().compareTo(p2.getId());

		final List<Proposicao> proposicoes = questao.getProposicoes().stream().sorted(comparatorPreposicoes)
				.collect(Collectors.toList());

		final Function<Proposicao, Integer> indexGetter = p -> proposicoes.indexOf(p);

		exec.getRespostas().stream().map(RespostaQuestao::getProposicao).distinct().sorted(comparatorPreposicoes)
				.map(p -> new Resposta(p, indexGetter)).forEach(respostasSelecionadas::add);

		proposicoes.stream().filter(Proposicao::isCorreta).sorted(comparatorPreposicoes)
				.map(p -> new Resposta(p, indexGetter)).forEach(respostasCorretas::add);

		pontuacao = new Pontuacao(questao, exec);
	}

	public JsonObject toJson() {

		final JsonObjectBuilder feedbackBuilder = JsonProvider.provider().createObjectBuilder();

		feedbackBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.APRESENTAR_FEEDBACK_QUESTAO.toString());
		feedbackBuilder.add("pontuacao", pontuacao.obterValor().multiply(new BigDecimal(100)).intValue());

		final JsonArrayBuilder arraySelecionadasBuilder = JsonProvider.provider().createArrayBuilder();

		respostasSelecionadas.stream().map(Resposta::toJson).forEach(arraySelecionadasBuilder::add);

		feedbackBuilder.add("selecionadas", arraySelecionadasBuilder.build());

		final JsonArrayBuilder arrayCorretasBuilder = JsonProvider.provider().createArrayBuilder();

		respostasCorretas.stream().map(Resposta::toJson).forEach(arrayCorretasBuilder::add);

		feedbackBuilder.add("corretas", arrayCorretasBuilder.build());

		return feedbackBuilder.build();
	}

	private class Resposta {

		private final String identificacao;

		private final String enunciado;

		private Resposta(Proposicao p, Function<Proposicao, Integer> indexGetter) {
			int indice = indexGetter.apply(p);

			identificacao = ((char) (indice + LETTER_A_INDEX)) + "";

			enunciado = p.getEnunciado();
		}

		private JsonObject toJson() {
			final JsonObjectBuilder respostaBuilder = JsonProvider.provider().createObjectBuilder();
			respostaBuilder.add("identificacao", identificacao + "");
			respostaBuilder.add("enunciado", enunciado);
			return respostaBuilder.build();
		}

	}

}
