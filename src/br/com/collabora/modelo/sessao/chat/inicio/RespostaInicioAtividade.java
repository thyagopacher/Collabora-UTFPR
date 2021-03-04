package br.com.collabora.modelo.sessao.chat.inicio;

import java.util.Optional;

public abstract class RespostaInicioAtividade {

	private String idSessao;

	private Optional<String> atividade;

	public RespostaInicioAtividade(String idSessao) {
		this(idSessao, null);
	}

	public RespostaInicioAtividade(String idSessao, String atividade) {
		this.idSessao = idSessao;
		this.atividade = Optional.ofNullable(atividade);
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}

	public Optional<String> getAtividade() {
		return atividade;
	}

	public void setAtividade(Optional<String> atividade) {
		this.atividade = atividade;
	}

	public abstract String getMensagem();

}

