package br.com.collabora.modelo.sessao.chat.inicio;

public class AlunoNaoAtivo extends RespostaInicioAtividade {

	public AlunoNaoAtivo(String idSessao) {
		super(idSessao);
	}

	@Override
	public String getMensagem() {
		return "Aluno não está logado!";
	}

}
