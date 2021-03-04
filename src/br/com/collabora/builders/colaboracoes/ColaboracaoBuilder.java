package br.com.collabora.builders.colaboracoes;

import java.io.File;

import modelObjects.Aluno;
import modelObjects.Colaboracao;

public interface ColaboracaoBuilder {

	ColaboracaoBuilder adicionarIdSessao(String idSessao);

	ColaboracaoBuilder adicionarIdExecucaoQuestao(String idExecucaoQuestao);

	ColaboracaoBuilder adicionarResponsavel(Aluno aluno);

	ColaboracaoBuilder adicionarMensagem(String mensagem);

	ColaboracaoBuilder adicionarArquivo(File file, String tipoConteudo, String nomeArquivo);

	Colaboracao construir();

	ColaboracaoBuilder reiniciar();

}
