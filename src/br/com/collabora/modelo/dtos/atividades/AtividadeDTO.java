package br.com.collabora.modelo.dtos.atividades;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import modelObjects.Pontuacao;

public class AtividadeDTO {

	private final String idAtividade;
	private final String nome;
	private final String dtfim;
	private final String hrfim;
	private final String numExercicios;
	private final List<String> conteudos = new ArrayList<>();

	private String fimExecucao;
	private String pontuacao;
	private String numLinks;
	private String numArquivos;
	private String numColaboracoes;

	public AtividadeDTO(String idAtividade, String nome, Date fim, Integer numExercicios) {
		this.idAtividade = idAtividade;
		this.nome = nome;
		this.dtfim = new SimpleDateFormat("dd/MM/yy").format(fim);
		this.hrfim = new SimpleDateFormat("hh:mm").format(fim);
		this.numExercicios = numExercicios.toString();
	}

	public AtividadeDTO(String idAtividade, String nome, Date fim, Integer numExercicios, 
			List<Pontuacao> pontuacoes, ColaboracoesAtividadeDTO colaboracoesAtividade) {
		this(idAtividade, nome, fim, numExercicios);
		this.fimExecucao = new SimpleDateFormat("dd/MM/yy hh:mm").format(colaboracoesAtividade.getFimUltimaExec());
		this.pontuacao = construirPontuacao(pontuacoes);
		this.numLinks = String.valueOf(colaboracoesAtividade.getNumLinks());
		this.numArquivos = String.valueOf(colaboracoesAtividade.getNumArquivos());
		this.numColaboracoes = String.valueOf(colaboracoesAtividade.getNumLinks() + colaboracoesAtividade.getNumArquivos());
	}

	private String construirPontuacao(List<Pontuacao> pontuacoes) {
		final BigDecimal resultado = calcularMediaAritmetica(
				pontuacoes.stream().map(Pontuacao::obterValor).collect(Collectors.toList()));
		return String.valueOf(resultado.multiply(new BigDecimal(100)).intValue());
	}

	private BigDecimal calcularMediaAritmetica(List<BigDecimal> values) {
		values = values.stream().filter(d -> d != null).collect(Collectors.toList());

		if (values.isEmpty()) {
			return null;
		} 

		BigDecimal sum = values.get(0);

		for (int i = 1; i < values.size(); i++) {
			sum = sum.add(values.get(i));
		}

		return new BigDecimal(sum.doubleValue() / values.size());
	}

	public String getIdAtividade() {
		return idAtividade;
	}

	public String getNome() {
		return nome;
	}

	public String getDtfim() {
		return dtfim;
	}

	public String getHrfim() {
		return hrfim;
	}

	public String getNumExercicios() {
		return numExercicios;
	}

	public void adicionarConteudo(String c) {
		if (c != null && !c.trim().isEmpty()) {
			this.conteudos.add(c);
		}
	}

	public List<String> getConteudos() {
		return conteudos;
	}

	public String getFimExecucao() {
		return fimExecucao;
	}

	public void setFimExecucao(String fimExecucao) {
		this.fimExecucao = fimExecucao;
	}

	public String getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(String pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getNumLinks() {
		return numLinks;
	}

	public void setNumLinks(String numLinks) {
		this.numLinks = numLinks;
	}

	public String getNumArquivos() {
		return numArquivos;
	}

	public void setNumArquivos(String numArquivos) {
		this.numArquivos = numArquivos;
	}

	public String getNumColaboracoes() {
		return numColaboracoes;
	}

	public void setNumColaboracoes(String numColaboracoes) {
		this.numColaboracoes = numColaboracoes;
	}

}
