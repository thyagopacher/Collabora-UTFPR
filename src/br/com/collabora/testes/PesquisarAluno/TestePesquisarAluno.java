package br.com.collabora.testes.PesquisarAluno;

import org.junit.Assert;
import org.junit.Test;

import businessService.login.pesquisarAluno.IPesquisarAluno;
import modelObjects.Aluno;

public class TestePesquisarAluno implements IPesquisarAluno{

	@Test
	public void PesquisarAlunoNulo() {
		
		Aluno aluno = null;
		
		Assert.assertNull(pesqAluno.pesquisar(aluno));
		
	}
	
	@Test
	public void PesquisarAlunoSemNome() {
		
		Aluno aluno = new Aluno();
		
		aluno.setRegistro("registro");
		aluno.setSenha("senha");
		
		Assert.assertNull(pesqAluno.pesquisar(aluno));
		
	}
	
	@Test
	public void PesquisarAlunoSemSenha() {
		
		Aluno aluno = new Aluno();
		
		aluno.setNome("nome");
		aluno.setRegistro("registro");
		
		Assert.assertNull(pesqAluno.pesquisar(aluno));
		
	}

}
