package modelObjects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FeedbackColaboracao {

	private final int idAluno;

	private final String nomeAluno;

	private final int numColaboracoes;

	public FeedbackColaboracao(int idAluno, String nomeAluno, int numColaboracoes) {
		this.idAluno = idAluno;
		this.nomeAluno = nomeAluno;
		this.numColaboracoes = numColaboracoes;
	}

	public int getIdAluno() {
		return idAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public int getNumColaboracoes() {
		return numColaboracoes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FeedbackColaboracao) {
			final FeedbackColaboracao fc = (FeedbackColaboracao) obj;

			return this.getIdAluno() == fc.getIdAluno();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idAluno).toHashCode();
	}

}
