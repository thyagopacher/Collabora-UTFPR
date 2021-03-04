package br.com.collabora.aluno.chat;

import java.util.Optional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import br.com.collabora.facades.ManipuladorFacades;
import br.com.collabora.facades.FacadeEJB;
import br.com.collabora.modelo.sessao.chat.inicio.RequisicaoInicioAtividade;
import br.com.collabora.modelo.sessao.chat.inicio.RespostaInicioAtividade;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;
import br.com.collabora.sessoes.alunos.chat.ControladorChats;

public class IniciarAtividadeAction extends ActionSupport implements ModelDriven<RequisicaoInicioAtividade> {

	private static final long serialVersionUID = 1L;

	private RequisicaoInicioAtividade req = new RequisicaoInicioAtividade();

	@Override
	public String execute() throws Exception {

		req.setIdSessao(ManipuladorFacades.getControlador(ControladorSessaoAluno.class).getId());

		if (!Optional.ofNullable(req.getIdAtividade()).isPresent()) {

			addActionError("Id para inicio de atividade inválido");

			return Action.ERROR;
		}
		
		final ControladorChats controladorChats = FacadeEJB.obterPelaClasseDoBean(ControladorChats.class);

		final RespostaInicioAtividade resp = controladorChats.iniciarAtividade(req);

		if (resp.getAtividade().isPresent()) {

			addActionMessage(resp.getMensagem());

			return SUCCESS;
		}

		addActionError(resp.getMensagem());

		return ERROR;
	}

	public RequisicaoInicioAtividade getReq() {
		return req;
	}

	public void setReq(RequisicaoInicioAtividade req) {
		this.req = req;
	}

	@Override
	public RequisicaoInicioAtividade getModel() {
		return req;
	}

}
