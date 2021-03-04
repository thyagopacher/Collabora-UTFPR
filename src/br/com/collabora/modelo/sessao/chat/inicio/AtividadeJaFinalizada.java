package br.com.collabora.modelo.sessao.chat.inicio;

public class AtividadeJaFinalizada extends RespostaInicioAtividade {

	public AtividadeJaFinalizada(String idSessao) {
		super(idSessao, null);
	}

	@Override
	public String getMensagem() {
		return "Atividade já foi finalizada!";
	}

}

