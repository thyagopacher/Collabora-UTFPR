package modelObjects;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ColaboracaoTextual implements Colaboracao {

	private static final long serialVersionUID = 1L;

	private final String id;

	private final String idExecucaoQuestao;

	private final Aluno responsavel;

	private final LocalDateTime dataHoraOcorrencia;

	private final String mensagem;

	public ColaboracaoTextual(String id, String idExecucaoQuestao, Aluno responsavel, LocalDateTime dataHoraOcorrencia,
			String mensagem) {
		this.id = id;
		this.idExecucaoQuestao = idExecucaoQuestao;
		this.responsavel = responsavel;
		this.dataHoraOcorrencia = dataHoraOcorrencia;
		this.mensagem = mensagem;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getIdExecucaoQuestao() {
		return idExecucaoQuestao;
	}

	@Override
	public Aluno getResponsavel() {
		return responsavel;
	}

	@Override
	public LocalDateTime getDataHoraOcorrencia() {
		return dataHoraOcorrencia;
	}

	public String getMensagem() {
		return mensagem;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColaboracaoTextual) {
			return this.getId().equals(((ColaboracaoTextual) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
