package modelObjects;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Proposicao implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private int idProposicao;
	private String enunciado;
	private String imagem;
	private String numero;
	private boolean correta;

	@Override
	public String getId() {
		return String.valueOf(idProposicao);
	}

	public int getIdProposicao() {
		return idProposicao;
	}

	public void setIdProposicao(int idProposicao) {
		this.idProposicao = idProposicao;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isCorreta() {
		return correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}

	@Override
	public String toString() {
		return "id " + this.idProposicao + " enunciado " + this.enunciado;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Proposicao) {
			return new EqualsBuilder().append(this.idProposicao, ((Proposicao) obj).getIdProposicao()).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idProposicao).toHashCode();
	}
}
