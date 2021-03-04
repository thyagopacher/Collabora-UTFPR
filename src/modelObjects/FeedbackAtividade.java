package modelObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import br.com.collabora.websocket.mensagens.alunos.RespostaAto;

public class FeedbackAtividade {

	final String nomeAtividade;

	final Grupo grupo;

	final List<Pontuacao> pontuacoes;

	final List<FeedbackColaboracao> feedbackColaboracoes;

	public FeedbackAtividade(Atividade atividade, Grupo grupo, List<FeedbackColaboracao> feedbackColaboracoes,
			List<Pontuacao> pontuacoes) {
		this.nomeAtividade = atividade.getNome();
		this.grupo = grupo;
		this.pontuacoes = pontuacoes;
		this.feedbackColaboracoes = feedbackColaboracoes;
	}

	public JsonObject toJson() {

		final JsonObjectBuilder feedbackBuilder = JsonProvider.provider().createObjectBuilder();

		final BigDecimal totalColaboracoes = new BigDecimal(
				feedbackColaboracoes.stream().mapToInt(FeedbackColaboracao::getNumColaboracoes).sum() + 0.00d);

		feedbackBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.APRESENTAR_FEEDBACK_ATIVIDADE.toString());
		feedbackBuilder.add("nomeAtividade", nomeAtividade);
		feedbackBuilder.add("totalColaboracoes", totalColaboracoes.intValue());

		if (!pontuacoes.isEmpty()) {
			final JsonArrayBuilder arrayBuilder = JsonProvider.provider().createArrayBuilder();

			int index = 0;
			for (Pontuacao p : pontuacoes.stream().sorted((o1, o2) -> o2.getIdQuestao().compareTo(o1.getIdQuestao()))
					.collect(Collectors.toList())) {
				final JsonObjectBuilder pontuacaoBuilder = JsonProvider.provider().createObjectBuilder();
				pontuacaoBuilder.add("questao", new Integer(++index).toString());
				pontuacaoBuilder.add("pontuacao", p.obterValor().multiply(new BigDecimal(100)).intValue());

				arrayBuilder.add(pontuacaoBuilder.build());
			}

			feedbackBuilder.add("pontuacoes", arrayBuilder);
		}

		final JsonArrayBuilder arrayBuilder = JsonProvider.provider().createArrayBuilder();

		for (Aluno aluno : grupo.getAlunos().stream().sorted((a1, a2) -> a2.getId().compareTo(a1.getId()))
				.collect(Collectors.toList())) {
			final Optional<FeedbackColaboracao> fc = feedbackColaboracoes.stream()
					.filter(f -> String.valueOf(f.getIdAluno()).equals(aluno.getId())).findFirst();

			final JsonObjectBuilder feedbackColaboracaoBuilder = JsonProvider.provider().createObjectBuilder();

			if (fc.isPresent()) {
				feedbackColaboracaoBuilder.add("aluno", fc.get().getNomeAluno());
				feedbackColaboracaoBuilder.add("numColaboracoes", fc.get().getNumColaboracoes());
				feedbackColaboracaoBuilder.add("porcentagemColaboracoes", new BigDecimal(fc.get().getNumColaboracoes())
						.divide(totalColaboracoes, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue());
			} else {
				feedbackColaboracaoBuilder.add("aluno", aluno.getNome());
				feedbackColaboracaoBuilder.add("numColaboracoes", 0);
				feedbackColaboracaoBuilder.add("porcentagemColaboracoes", 0);
			}

			arrayBuilder.add(feedbackColaboracaoBuilder.build());
		}
		feedbackBuilder.add("colaboracoes", arrayBuilder);

		return feedbackBuilder.build();
	}

}
