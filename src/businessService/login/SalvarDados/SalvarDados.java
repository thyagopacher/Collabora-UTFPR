package businessService.login.SalvarDados;

import modelObjects.Aluno;
import modelObjects.Gerente;
import modelObjects.Professor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import businessService.login.manterAluno.IManterAluno;
import businessService.login.manterGerente.IGerenciarGerente;
import businessService.login.manterProfessor.IManterProfessor;

public class SalvarDados implements ISalDados, IManterProfessor, IManterAluno, IGerenciarGerente{
	
	/* Utiliza uma expressão regular (re) para validar as linhas do arquivo
	 * passado como paramentro de entrada.
	 * 
	 * 
	 * Entrada: A entrada é composta por um arquivo de texto contento: 
	 *  	nome do aluno: entre 10 e 99 caracteres do alfabeto, não case sensitive.
	 *		registro do aluno: entre 6 e 19 caracteres numéricos.
	 * 		senha: entre 6 e 9 caracteres numéricos.
	 *  
	 * Os retornos possíveis são 4: sucess, mensagem de erro, erro de exeção ou a string error.
	 * 		Sucess: siguinifica que o arquivo passado possui a gramática necessária.
	 * 		Mensagem de erro: aponta ao usuário qual linha deve ser corrida.
	 * 		Erro de exeção: aponta ao desenvolvedor o que deve ser corrigido.
	 * 		String Error: a entrada do arquivo é nula ou não foi possivel salvar a lista de professores corretamente. 
	 * */
	@Override
	public String validarEntradaAluno(File file) {
		
		if(file == null) return "error";
		
		String re = "[a-zA-Z ]{10,99}-[0-9]{6,19}-[0-9]{6,9}";
		String[] partes;
		
		Pattern pattern = Pattern.compile(re);
		Matcher matcher;
		
		List<Aluno> lista= new ArrayList<>();
		Aluno aluno;
		int ultimo = 0;
				
				
		try {
			ArrayList<String> lines = (ArrayList<String>) FileUtils.readLines(file);

			for (String line : lines) {
				
				//System.out.println(line);
				
				matcher = pattern.matcher(line);
				
				if(matcher.matches()){
					
					partes = line.split("-");
					
					aluno = new Aluno();
					
					aluno.setNome(partes[0]);
					aluno.setRegistro(partes[1]);
					aluno.setSenha(partes[2]);
					
					lista.add(aluno);
					
				}else
					ultimo = this.farthestPoint(pattern, line);
					return "O arquivo não corresponde as especificações, erro na linha "+(lines.indexOf(line)+1)+
					" aproximadamente na posição "+ultimo;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		
		if(mantAluno.inserir(lista))
			return "success";
		else
			return "error";
	}

	@Override
	/* Utiliza uma expressão regular (re) para validar as linhas do arquivo
	 * passado como paramentro de entrada.
	 * 
	 * 
	 * Entrada: A entrada é composta por um arquivo de texto contento: 
	 *  	nome do professor: entre 10 e 99 caracteres do alfabeto, não case sensitive.
	 *		registro do professor: entre 6 e 19 caracteres alphaNumerico, não case sensitive.
	 * 		senha: entre 6 e 9 caracteres numéricos.
	 *  
	 * Os retornos possíveis são 4: sucess, mensagem de erro, erro de exeção ou a string error.
	 * 		Sucess: siguinifica que o arquivo passado possui a gramática necessária.
	 * 		Mensagem de erro: aponta ao usuário qual linha deve ser corrida.
	 * 		Erro de exeção: aponta ao desenvolvedor o que deve ser corrigido.
	 * 		String Error: a entrada do arquivo é nula ou não foi possivel salvar a lista de professores corretamente. 
	 * */
	public String validarEntradaProfessor(File file) {
		
		if(file == null) return "error";
		
		String re = "[a-zA-Z ]{10,99}-[a-zA-Z[0-9]]{6,19}-[0-9]{6,9}";
		String[] partes;
		
		Pattern pattern = Pattern.compile(re);
		Matcher matcher;
		
		List<Professor> lista= new ArrayList<>();
		Professor professor;
		int ultimo = 0;
				
				
		try {
			ArrayList<String> lines = (ArrayList<String>) FileUtils.readLines(file);

			for (String line : lines) {
				
				matcher = pattern.matcher(line);
				
				if(matcher.matches()){
					
					partes = line.split("-");
					
					professor = new Professor();
					
					professor.setNome(partes[0]);
					professor.setRegistro(partes[1]);
					professor.setSenha(partes[2]);
					
					lista.add(professor);
					
				}else
					ultimo = this.farthestPoint(pattern, line);
					return "O arquivo não corresponde as especificações, erro na linha "+(lines.indexOf(line)+1)+
						" aproximadamente na posição "+ultimo;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		
		if(mantProfessor.inserir(lista))
			return "success";
		else
			return "error";
	}
	
	/* Utiliza uma expressão regular (re) para validar as linhas do arquivo
	 * passado como paramentro de entrada.
	 * 
	 * 
	 * Entrada: A entrada é composta por um arquivo de texto contento: 
	 *  	nome do Gerente: entre 10 e 99 caracteres do alfabeto, não case sensitive.
	 * 		senha: entre 6 e 9 caracteres numéricos.
	 *  
	 * Os retornos possíveis são 4: sucess, mensagem de erro, erro de exeção ou a string error.
	 * 		Sucess: siguinifica que o arquivo passado possui a gramática necessária.
	 * 		Mensagem de erro: aponta ao usuário qual linha deve ser corrida.
	 * 		Erro de exeção: aponta ao desenvolvedor o que deve ser corrigido.
	 * 		String Error: a entrada do arquivo é nula ou não foi possivel salvar a lista de professores corretamente. 
	 * */
	@Override
	public String validarEntradaGerente(File file) {
		if(file == null) return "error";
		
		String re = "[a-zA-Z ]{10,99}-[0-9]{6,9}";
		String[] partes;
		
		Pattern pattern = Pattern.compile(re);
		Matcher matcher;
		
		List<Gerente> lista= new ArrayList<>();
		Gerente gerente;
		int ultimo = 0;
				
		try {
			ArrayList<String> lines = (ArrayList<String>) FileUtils.readLines(file);

			for (String line : lines) {
				
				matcher = pattern.matcher(line);
				
				if(matcher.matches()){
					
					partes = line.split("-");
					
					gerente = new Gerente();
					
					gerente.setNome(partes[0]);
					gerente.setSenha(partes[2]);
					
					lista.add(gerente);
					
				}else
					ultimo = this.farthestPoint(pattern, line);
					return "O arquivo não corresponde as especificações, erro na linha "+(lines.indexOf(line)+1)+
							" aproximadamente na posição "+ultimo;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		
		if(g.inserir(lista))
			return "success";
		else
			return "error";
	}
	
	/*
	 * 		Farthest Point
	 * 
	 * 	Recebe um patter e a string analisada que não correspode a este patter.
	 * 	Verifica qual o ponto mais distante de uma sub-string que se encaixe no patter.
	 * 
	 * Retorna: o ultimo ponto onde existe uma sub-string que se encaixe no patter.
	 * 
	 * */
	private int farthestPoint(Pattern pattern, String input) {
	    for (int i = input.length() - 1; i > 0; i--) {
	        
	        Matcher matcher = pattern.matcher(input.substring(0, i));

	        if (!matcher.matches() && matcher.hitEnd()) {
	            return i;
	        }
	    }
	    return 0;
	}

}
