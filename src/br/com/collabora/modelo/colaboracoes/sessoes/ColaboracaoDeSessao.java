package br.com.collabora.modelo.colaboracoes.sessoes;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import modelObjects.Aluno;
import modelObjects.Colaboracao;

public class ColaboracaoDeSessao implements Colaboracao {

	private static final long serialVersionUID = 1L;

	private final String idSessao;

	private final Colaboracao colaboracao;

	public ColaboracaoDeSessao(String idSessao, Colaboracao colaboracao) {
		this.idSessao = idSessao;
		this.colaboracao = colaboracao;
	}

	public String getIdSessao() {
		return idSessao;
	}

	@Override
	public String getId() {
		return colaboracao.getId();
	}

	@Override
	public String getIdExecucaoQuestao() {
		return colaboracao.getIdExecucaoQuestao();
	}

	@Override
	public Aluno getResponsavel() {
		return colaboracao.getResponsavel();
	}

	@Override
	public LocalDateTime getDataHoraOcorrencia() {
		return colaboracao.getDataHoraOcorrencia();
	}

	public Colaboracao getColaboracao() {
		return colaboracao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColaboracaoDeSessao) {
			final ColaboracaoDeSessao outro = (ColaboracaoDeSessao) obj;
			return new EqualsBuilder().append(idSessao, outro.getIdSessao())
					.append(this.colaboracao.getId(), outro.getColaboracao().getId()).build();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idSessao).build();
	}

}
