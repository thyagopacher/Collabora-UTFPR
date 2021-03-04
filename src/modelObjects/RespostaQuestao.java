package modelObjects;

import java.util.UUID;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class RespostaQuestao implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private final String id = UUID.randomUUID().toString();

	private final Proposicao proposicao;

	private final Aluno aluno;

	public RespostaQuestao(Proposicao proposicao) {
		this(proposicao, null);
	}

	public RespostaQuestao(Proposicao proposicao, Aluno aluno) {
		this.proposicao = proposicao;
		this.aluno = aluno;
	}

	@Override
	public String getId() {
		return id;
	}

	public Proposicao getProposicao() {
		return proposicao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public boolean dadaPor(Aluno a) {
		return aluno.equals(a);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RespostaQuestao) {
			return this.getId().equals(((RespostaQuestao) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
