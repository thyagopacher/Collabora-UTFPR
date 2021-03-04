package br.com.collabora.facades.sessao;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

@Stateless
public class FacadeSessaoAlunoImpl implements FacadeSessaoAluno {

	private @Inject ControladorSessaoAluno controladorSessaoAluno;

	@Override
	public ControladorSessaoAluno getControlador() {
		return controladorSessaoAluno;
	}
}
