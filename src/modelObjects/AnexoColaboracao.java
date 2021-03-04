package modelObjects;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AnexoColaboracao implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String id;

	private final String tipoMidia;

	private final Long tamanho;

	private final String nome;

	public AnexoColaboracao(String id, String tipoMidia, Long tamanho, String nome) {
		this.id = id;
		this.tipoMidia = tipoMidia;
		this.tamanho = tamanho;
		this.nome = nome;
	}

	public String getId() {
		return id;
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
		if (obj instanceof AnexoColaboracao) {
			return this.getId().equals(((AnexoColaboracao) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
