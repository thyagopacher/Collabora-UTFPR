package br.com.collabora.modelo.sessao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.websocket.Session;

import modelObjects.Aluno;
import modelObjects.Grupo;

public class SessaoAluno implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String id;

	private Optional<Aluno> aluno = Optional.empty();

	private Optional<Grupo> grupo = Optional.empty();

	private List<Session> sessaoWs = new ArrayList<>();

	public SessaoAluno(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean temAlunoLogado() {
		return aluno.isPresent();
	}

	public boolean mesmoAluno(Aluno a) {
		return temAlunoLogado() && aluno.get().equals(a);
	}

	public Aluno getAluno() {
		return aluno.orElse(null);
	}

	public boolean temSessaoWsAtiva() {
		return sessaoWs.stream().filter(ws -> ws.isOpen()).count() > 0;
	}

	public void removerSessaoWs(Session s) {
		sessaoWs.remove(s);
	}

	public void incluirSessaoWs(Session s) {
		sessaoWs.add(s);
	}

	public List<Session> getSessoesWs() {
		return sessaoWs;
	}

	public void atualizarDados(Optional<Aluno> aluno, Optional<Grupo> grupo) {
		this.aluno = aluno;
		this.grupo = aluno.isPresent() && grupo.isPresent() ? grupo : Optional.empty();
	}

	public Optional<Grupo> getGrupo() {
		return grupo;
	}

	public void setGrupo(Optional<Grupo> grupo) {
		this.grupo = grupo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SessaoAluno) {
			return this.getId().equals(((SessaoAluno) obj).getId());
		}
		return false;
	}

}
