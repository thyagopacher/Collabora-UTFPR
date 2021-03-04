package br.com.collabora.testes.ManterAluno;

import org.junit.Assert;
import org.junit.Test;

import businessService.login.manterAluno.IManterAluno;
import modelObjects.Aluno;

public class TesteManterAluno implements IManterAluno {
	@Test
	public void inserirAlunoNull() {
		Aluno aluno = null;

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}

	@Test
	public void inserirAlunoCamposGrandes() {
		Aluno aluno = new Aluno();

		aluno.setNome("NomeMuitoGrandeExtremamenteExageradoQueNenhumaPesso"
				+ "aPoderiaTerPorqueEMuitoGrandeMesmoDeVerdadePessoa");
		aluno.setRegistro("321890328109382109382109832");

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}

	@Test
	public void inserirAlunoCamposVazios() {
		Aluno aluno = new Aluno();

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}
	
	@Test
	public void inserirAlunoSomenteNome() {
		Aluno aluno = new Aluno();

		aluno.setNome("Aluno");

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}
	
	@Test
	public void inserirAlunoSomenteRegistro() {
		Aluno aluno = new Aluno();

		aluno.setRegistro("registro");

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}
	

	@Test
	public void inserirAlunoSomenteSenha() {
		Aluno aluno = new Aluno();

		aluno.setSenha("senha");

		Assert.assertEquals(false, mantAluno.inserir(aluno));
	}
	
}