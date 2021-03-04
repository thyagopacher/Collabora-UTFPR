package br.com.collabora.aluno.chat;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class RedirecionarSemMinAlunosAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String MSG_SEM_MIN_ALUNOS = "Redirecionamento feito pois não há mínimo de alunos para dar continução a Atividade.";

	@Override
	public String execute() throws Exception {

		addActionError(MSG_SEM_MIN_ALUNOS);

		return Action.SUCCESS;
	}

}
