package br.com.collabora.modelo.sessao.chat.inicio;

public class GrupoInsuficiente extends RespostaInicioAtividade {

	public GrupoInsuficiente(String idSessao) {
		super(idSessao, null);
	}

	@Override
	public String getMensagem() {
		return "Número de alunos logados é insuficiênte.";
	}

}

