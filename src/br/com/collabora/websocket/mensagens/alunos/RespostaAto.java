package br.com.collabora.websocket.mensagens.alunos;

import java.util.stream.Stream;

import org.apache.commons.lang.WordUtils;

public enum RespostaAto {
	ATUALIZACAO_SESSOES, 
	REDIRECIONAMENTO_PARA_CHAT, 
	COLABORACAO, 
	TEMPO_DURACAO_ATIVIDADE, 
	TROCAR_QUESTAO, 
	NOTIFICAR_EMPATE, 
	APRESENTAR_FEEDBACK_QUESTAO, 
	APRESENTAR_FEEDBACK_ATIVIDADE,
	REDIRECIONAR_APOS_ATIVIDADE;

	private final String descricao;

	RespostaAto() {
		descricao = Stream.of(this.name().split("_")).map(String::toLowerCase).map(WordUtils::capitalize).reduce("",
				String::concat);
	}

	@Override
	public String toString() {
		return descricao;
	}
}
