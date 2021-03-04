package br.com.collabora.testes.colaboracoes;

import org.junit.Test;

import br.com.collabora.builders.colaboracoes.ColaboracaoBuilder;
import br.com.collabora.builders.colaboracoes.ColaboracaoBuilderPadrao;
import junit.framework.Assert;
import modelObjects.Aluno;
import modelObjects.Colaboracao;
import modelObjects.ColaboracaoTextual;

public class ColaboracaoTeste {

	private final ColaboracaoBuilder colaboracaoBuilder = new ColaboracaoBuilderPadrao();

	@Test
	public void criarColaboracaoTextual() {

		final Aluno a = new Aluno("Teste", 1);
		final String mensagemColaboracao = "É  gooolll";

		final Colaboracao c = colaboracaoBuilder.reiniciar().adicionarMensagem(mensagemColaboracao)
				.adicionarIdSessao("").adicionarIdExecucaoQuestao("").adicionarResponsavel(a).construir();

		Assert.assertEquals(ColaboracaoTextual.class, c.getClass());
		Assert.assertEquals(a, c.getResponsavel());
		Assert.assertNotNull(c.getId());
		Assert.assertEquals(mensagemColaboracao, ((ColaboracaoTextual) c).getMensagem());
	}

}
