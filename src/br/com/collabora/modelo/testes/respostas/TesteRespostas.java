package br.com.collabora.modelo.testes.respostas;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TesteRespostas {
	
	
	@Test
	public void preenchimentoAutomaticoProposicoes(){
		
		final List<Character> proposicoes = Arrays.asList('a', 'b', 'c', 'd', 'e');
		
		for(int i=0; i<proposicoes.size() ; i++){
			final Character c = proposicoes.get(i);
			final int valorInt = Character.getNumericValue(c);
			final char retornoC = (char) (valorInt + 87);
			
			
			
			System.out.println(String.format("Proposição %s, número %d, retorno %s", c.toString(), valorInt, retornoC) );
		}
		
	}

}
