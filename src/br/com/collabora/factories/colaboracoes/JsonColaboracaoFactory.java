package br.com.collabora.factories.colaboracoes;

import javax.json.JsonObject;

import modelObjects.Colaboracao;
import modelObjects.ColaboracaoComAnexo;
import modelObjects.ColaboracaoTextual;

public interface JsonColaboracaoFactory {

	JsonObject criar(String idSessao, Colaboracao colaboracao);

	static <T extends Colaboracao> JsonColaboracaoFactory obterPorTipoColaboracao(Class<T> tipo) {
		if (ColaboracaoTextual.class.equals(tipo)) {
			return new JsonColaboracaoTextualFactory();
		} else if (ColaboracaoComAnexo.class.equals(tipo)) {
			return new JsonColaboracaoComAnexoFactory();
		}

		throw new IllegalArgumentException();
	}

}
