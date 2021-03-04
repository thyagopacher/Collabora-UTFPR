package br.com.collabora.modelo.sessao.chat.inicio;

public class AtividadeIniciada extends RespostaInicioAtividade {

	public AtividadeIniciada(String idSessao, String atividade) {
		super(idSessao, atividade);
	}

	@Override
	public String getMensagem() {
		return String.format("Atividade %s iniciada", this.getAtividade().orElse("-1"));
	}

}


