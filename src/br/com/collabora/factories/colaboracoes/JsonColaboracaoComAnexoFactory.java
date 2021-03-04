package br.com.collabora.factories.colaboracoes;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import modelObjects.Colaboracao;
import modelObjects.ColaboracaoComAnexo;

public class JsonColaboracaoComAnexoFactory implements JsonColaboracaoFactory {

	private final String VALOR_URL_LINK = "/Ovaces/rest/alunos/colaboracoes/anexos/obterPorId.txt?id=%s";

	private final String TAG_ID_SESSAO_ORIGEM = "idSessaoOrigem";
	private final String TAG_ANEXO = "anexo";
	private final String TAG_LINK = "link";
	private final String TAG_AUTOR = "autor";
	private final String TAG_MENSAGEM = "mensagem";

	public JsonObject criar(String idSessao, Colaboracao c) {
		final ColaboracaoComAnexo colaboracao = (ColaboracaoComAnexo) c;
		final JsonObjectBuilder objectBuilder1 = JsonProvider.provider().createObjectBuilder();
		objectBuilder1.add(TAG_ID_SESSAO_ORIGEM, idSessao);
		objectBuilder1.add(TAG_ANEXO, true);
		objectBuilder1.add(TAG_LINK, String.format(VALOR_URL_LINK, colaboracao.getId()));
		objectBuilder1.add("nomeArquivo", colaboracao.getNome());
		objectBuilder1.add(TAG_MENSAGEM, colaboracao.getNome());
		objectBuilder1.add(TAG_AUTOR, colaboracao.getResponsavel().getNome());

		return objectBuilder1.build();
	}

}
