package br.com.collabora.builders.colaboracoes;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import modelObjects.Aluno;
import modelObjects.Colaboracao;
import modelObjects.ColaboracaoComAnexo;
import modelObjects.ColaboracaoTextual;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ColaboracaoBuilderPadrao implements ColaboracaoBuilder {

	private String idSessao;

	private String idExecucaoQuestao;

	private Aluno aluno;

	private String mensagem;

	private File file;

	private String tipoConteudo;

	private String nomeArquivo;

	@Override
	public ColaboracaoBuilder adicionarIdSessao(String idSessao) {
		this.idSessao = idSessao;
		return this;
	}

	@Override
	public ColaboracaoBuilder adicionarIdExecucaoQuestao(String idExecucaoQuestao) {
		this.idExecucaoQuestao = idExecucaoQuestao;
		return this;
	}

	@Override
	public ColaboracaoBuilder adicionarResponsavel(Aluno aluno) {
		this.aluno = aluno;
		return this;
	}

	@Override
	public ColaboracaoBuilder adicionarMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}

	@Override
	public ColaboracaoBuilder adicionarArquivo(File file, String tipoConteudo, String nomeArquivo) {
		this.file = file;
		this.tipoConteudo = tipoConteudo;
		this.nomeArquivo = nomeArquivo;
		return this;
	}

	@Override
	public ColaboracaoBuilder reiniciar() {
		this.idSessao = null;
		this.idExecucaoQuestao = null;
		this.aluno = null;
		this.mensagem = null;
		this.file = null;
		this.nomeArquivo = null;
		this.tipoConteudo = null;
		return this;
	}

	@Override
	public Colaboracao construir() {
		if (!Optional.ofNullable(idSessao).isPresent() || !Optional.ofNullable(idExecucaoQuestao).isPresent()) {
			throw new IllegalArgumentException();
		}

		if (Optional.ofNullable(mensagem).isPresent() && !mensagem.trim().isEmpty()) {
			return criarColaboracaoTextual(idSessao, idExecucaoQuestao, aluno);
		}

		if (Optional.ofNullable(file).isPresent() && Optional.ofNullable(tipoConteudo).isPresent()
				&& Optional.ofNullable(nomeArquivo).isPresent() && !tipoConteudo.trim().isEmpty()
				&& !nomeArquivo.trim().isEmpty()) {
			return criarColaboracaoPorArquivo(idSessao, idExecucaoQuestao, aluno);
		}

		throw new NotImplementedException();
	}

	private Colaboracao criarColaboracaoPorArquivo(String idSessao, String idExecucaoQuestao, Aluno aluno) {
		return new ColaboracaoComAnexo(UUID.randomUUID().toString(), idExecucaoQuestao, aluno, LocalDateTime.now(),
				file, tipoConteudo, file.length(), nomeArquivo);

	}

	private Colaboracao criarColaboracaoTextual(String idSessao, String idExecucaoQuestao, Aluno aluno) {
		return new ColaboracaoTextual(UUID.randomUUID().toString(), idExecucaoQuestao, aluno, LocalDateTime.now(),
				mensagem);
	}

}
