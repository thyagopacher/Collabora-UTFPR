package br.com.collabora.modelo.sessao.chat.inicio;

public class GrupoComAtividadeAtiva extends RespostaInicioAtividade {

	public GrupoComAtividadeAtiva(String idSessao, String atividade) {
		super(idSessao, atividade);
	}

	@Override
	public String getMensagem() {
		return String.format("Redirecionado para atividade já ativa no Grupo (%s)!", getAtividade().orElse("-1"));
	}

}

