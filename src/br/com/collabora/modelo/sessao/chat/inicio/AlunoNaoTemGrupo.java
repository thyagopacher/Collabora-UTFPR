package br.com.collabora.modelo.sessao.chat.inicio;

public class AlunoNaoTemGrupo extends RespostaInicioAtividade {

	public AlunoNaoTemGrupo(String idSessao) {
		super(idSessao);
	}

	@Override
	public String getMensagem() {
		return "Alunos está sem grupo para iniciar atividade";
	}

}
