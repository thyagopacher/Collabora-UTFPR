package br.com.collabora.factories.colaboracoes;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import modelObjects.Colaboracao;
import modelObjects.ColaboracaoTextual;

public class JsonColaboracaoTextualFactory implements JsonColaboracaoFactory {

	private final String TAG_ID_SESSAO_ORIGEM = "idSessaoOrigem";
	private final String TAG_ANEXO = "anexo";
	private final String TAG_AUTOR = "autor";
	private final String TAG_MENSAGEM = "mensagem";

	public JsonObject criar(String idSessao, Colaboracao c) {
		final ColaboracaoTextual colaboracao = (ColaboracaoTextual) c;
		final JsonObjectBuilder objectBuilder1 = JsonProvider.provider().createObjectBuilder();
		objectBuilder1.add(TAG_ID_SESSAO_ORIGEM, idSessao);
		objectBuilder1.add(TAG_ANEXO, false);
		objectBuilder1.add(TAG_MENSAGEM, colaboracao.getMensagem());
		objectBuilder1.add(TAG_AUTOR, colaboracao.getResponsavel().getNome());

		return objectBuilder1.build();
	}

}
