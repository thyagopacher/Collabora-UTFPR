package br.com.collabora.modelo.sessao.chat.inicio;

public class AtividadeInexistente extends RespostaInicioAtividade {

	public AtividadeInexistente(String idSessao) {
		super(idSessao);
	}

	@Override
	public String getMensagem() {
		return "Atividade solicitada não existe";
	}

}
