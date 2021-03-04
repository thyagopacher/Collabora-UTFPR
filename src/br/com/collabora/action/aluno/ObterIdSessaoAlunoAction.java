package br.com.collabora.action.aluno;

import com.opensymphony.xwork2.ActionSupport;

import br.com.collabora.facades.ManipuladorFacades;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

public class ObterIdSessaoAlunoAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String record;

	@Override
	public String execute() throws Exception {
		setRecord(ManipuladorFacades.getControlador(ControladorSessaoAluno.class).getId());

		return SUCCESS;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

}
