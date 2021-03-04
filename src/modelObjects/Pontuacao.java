package modelObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class Pontuacao {

	private String idQuestao;

	private int numTotalProposicoes;

	private int numTotalProposicoesCorretas;

	private int numProposicoesCorretas;

	private int numProposicoesIncorretas;

	private final NumberFormat nf;

	private Pontuacao() {
		nf = NumberFormat.getIntegerInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
	}

	public Pontuacao(Questao questao, ExecucaoQuestao exec) {
		this();
		this.idQuestao = questao.getId();
		this.numTotalProposicoes = questao.getProposicoes().size();
		this.numTotalProposicoesCorretas = (int) questao.getProposicoes().stream().filter(Proposicao::isCorreta)
				.count();
		this.numProposicoesCorretas = (int) exec.getRespostas().stream().map(RespostaQuestao::getProposicao)
				.filter(questao::isCorreta).count();
		this.numProposicoesIncorretas = (int) exec.getRespostas().stream().map(RespostaQuestao::getProposicao)
				.filter(p -> !questao.isCorreta(p)).count();

	}

	public Pontuacao(String idQuestao, int numTotalProposicoes, int numTotalProposicoesCorretas,
			int numProposicoesCorretas, int numProposicoesIncorretas) {
		this();
		this.idQuestao = idQuestao;
		this.numTotalProposicoes = numTotalProposicoes;
		this.numTotalProposicoesCorretas = numTotalProposicoesCorretas;
		this.numProposicoesCorretas = numProposicoesCorretas;
		this.numProposicoesIncorretas = numProposicoesIncorretas;

	}

	public String getIdQuestao() {
		return idQuestao;
	}

	public BigDecimal obterValor() {
		return new BigDecimal((numTotalProposicoes
				- (numTotalProposicoesCorretas - (numProposicoesCorretas - numProposicoesIncorretas))))
						.divide(new BigDecimal(numTotalProposicoes), 2 , RoundingMode.HALF_UP);
	}

}
