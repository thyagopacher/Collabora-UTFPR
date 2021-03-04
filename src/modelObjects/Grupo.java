package modelObjects;

import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Grupo implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private List<Aluno> alunos;
	private int idGrupo;
	private Turma turma;
	// private Atividade atividade;

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public boolean temComoMembro(Aluno aluno) {
		return alunos.stream().anyMatch(aluno::equals);
	}

	@Override
	public String getId() {
		return String.valueOf(this.getIdGrupo());
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	// public Atividade getAtividade() {
	// return atividade;
	// }
	//
	// public void setAtividade(Atividade atividade) {
	// this.atividade = atividade;
	// }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Grupo) {
			return this.getIdGrupo().equals(((Grupo) obj).getIdGrupo());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getIdGrupo()).toHashCode();
	}

}
