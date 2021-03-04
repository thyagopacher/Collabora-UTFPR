package modelObjects;

import java.util.List;
import java.util.stream.Collectors;

public class Questao implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private Integer idQuestao;
	private String dificuldade, imagem, enunciado;
	private double peso;
	private List<Proposicao> proposicoes;
	private Referencia referencia;
	private Conteudo conteudo;

	@Override
	public String getId() {
		return String.valueOf(this.getIdQuestao());
	}

	public Integer getIdQuestao() {
		return idQuestao;
	}

	public void setIdQuestao(int idQuestao) {
		this.idQuestao = idQuestao;
	}

	public String getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public List<Proposicao> getProposicoes() {
		return proposicoes;
	}
	
	public boolean isCorreta(Proposicao proposicao) {
		return proposicoes.stream().filter(Proposicao::isCorreta).anyMatch(proposicao::equals);
	}

	public void setProposicoes(List<Proposicao> proposicoes) {
		this.proposicoes = proposicoes;
	}

	public Referencia getReferencia() {
		return referencia;
	}

	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}

	public Conteudo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "questão" + this.idQuestao + " enunciado " + this.enunciado + " proposicao " + this.proposicoes;

	}
}
