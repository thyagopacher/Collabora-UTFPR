package br.com.collabora.modelo.sessao.chat.inicio;

import java.io.Serializable;

public class RequisicaoInicioAtividade implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idSessao;

	private Integer idAtividade;

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}

	public Integer getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}

}
