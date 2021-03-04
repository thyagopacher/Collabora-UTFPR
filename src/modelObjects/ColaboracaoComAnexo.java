package modelObjects;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ColaboracaoComAnexo implements Colaboracao {

	private static final long serialVersionUID = 1L;

	private final String id;

	private final String idExecucaoQuestao;

	private final Aluno responsavel;

	private final LocalDateTime dataHoraOcorrencia;

	private File arquivo;

	private final String tipoMidia;

	private final Long tamanho;

	private final String nome;

	public ColaboracaoComAnexo(String id, String idExecucaoQuestao, Aluno responsavel, LocalDateTime dataHoraOcorrencia,
			File arquivo, String tipoMidia, Long tamanho, String nome) {
		this.id = id;
		this.idExecucaoQuestao = idExecucaoQuestao;
		this.responsavel = responsavel;
		this.dataHoraOcorrencia = dataHoraOcorrencia;
		this.arquivo = arquivo;
		this.tipoMidia = tipoMidia;
		this.tamanho = tamanho;
		this.nome = nome;
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

	public void removerReferenciaArquivo() {
		this.arquivo = null;
	}

	public File getArquivo() {
		return arquivo;
	}

	public String getTipoMidia() {
		return tipoMidia;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColaboracaoComAnexo) {
			return this.getId().equals(((ColaboracaoComAnexo) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
