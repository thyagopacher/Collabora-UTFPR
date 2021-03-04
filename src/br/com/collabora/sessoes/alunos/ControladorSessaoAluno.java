package br.com.collabora.sessoes.alunos;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collabora.dao.alunos.AlunoDao;
import br.com.collabora.dao.grupos.GrupoDao;
import br.com.collabora.facades.Controlador;
import modelObjects.Aluno;
import modelObjects.Grupo;

@Named
@SessionScoped
public class ControladorSessaoAluno implements Controlador {

	private static final long serialVersionUID = 1L;

	private final String id = UUID.randomUUID().toString();

	private @Inject AlunoDao alunoDao;

	private @Inject GrupoDao grupoDao;

	private @Inject ControladorSessoesAlunos controladorGeral;

	public boolean existeAlunoLogado() {
		return controladorGeral.estaAtiva(id);
	}

	public void atualizarSessao(Aluno aluno) {
		final Optional<Aluno> a = alunoDao.obter(aluno);
		controladorGeral.atualizarSessao(id, a, grupoDao.obterGrupoAluno(a.orElse(null)));
	}

	@PreDestroy
	public void removerSessao() {
		controladorGeral.removerSessao(id);
	}

	public String getId() {
		return id;
	}

	public Optional<Grupo> getGrupo() {
		return controladorGeral.obterGrupo(id);
	}

}
