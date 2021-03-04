package br.com.collabora.modelo.sessao.chat;

import java.io.Serializable;
import java.util.Optional;

import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.Grupo;

public class AtividadeGrupo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Grupo grupo;

	private Optional<Atividade> atividade;

	public AtividadeGrupo(Grupo grupo) {
		this(grupo, null);
	}

	public AtividadeGrupo(Grupo grupo, Atividade atividade) {
		this.grupo = grupo;
		this.atividade = Optional.ofNullable(atividade);
	}

	public boolean temAtividade() {
		return atividade.isPresent();
	}

	public boolean temGrupo(Aluno aluno) {
		return this.getGrupo().getAlunos().stream().filter(aluno::equals).findAny().isPresent();
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Optional<Atividade> getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = Optional.ofNullable(atividade);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AtividadeGrupo) {
			return this.getGrupo().getId().equals(((AtividadeGrupo) obj).getGrupo().getId());
		}
		return false;
	}

}
