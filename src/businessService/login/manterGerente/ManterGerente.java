package businessService.login.manterGerente;

import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarGerente.IProcurarGerente;
import modelObjects.Gerente;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferecem o serviço de persistencia com a aplicação de banco de dados (IPersistencia)
 * 	- Que oferecem o seriço de pesquisar Gerentes (IProcurarGerente)
 * 
 * Requeridas:
 * 	- Especifica o serviço de manter Gerente (ImanterGerente)
 *  
 * */
public class ManterGerente implements IManterGerente,IPersistencia,IProcurarGerente {
	
	private String query;
	
	/*		
	 * 		INSERIR
	 * 
	 * 		Recebe um objeto gerente e o insere no banco de dados.
	 * 
	 * 		Retorna verdadeiro caso a inserção ocorrer de maneira correta, ou seja, 
	 * 	o resultado do ExecuteUpdate ser diferente de 0.
	 * 
	 * 		Retorna falso caso o objeto passado seja nulo, quando o resultado do 
	 * 	ExecuteUpdade ser igual a zero ou se já existe um gerente com este nome e senha.
	 * 
	 * */
	public boolean inserir (Gerente gerente){
		
		if(gerente == null) return false;
		
		if(null == pesqGerente.pesquisar(gerente)){
			
			query = "insert into gerente(nome, senha) values ('"+gerente.getNome().toUpperCase()+
					"', '"+gerente.getSenha()+"');";
			
			return (pers.ExecuteUpdate(query) > 0);
		}
		
		return false;
		
	}
	
	/*		
	 * 		Modificar
	 * 
	 * 		Recebe um objeto gerente e o modifica no banco de dados, segundo o id deste objeto.
	 * 
	 * 		Retorna verdadeiro caso a modificação ocorrer de maneira correta, ou seja, 
	 * 	o resultado do ExecuteUpdate ser diferente de 0.
	 * 
	 * 		Retorna falso caso o objeto passado seja nulo ou o resultado do 
	 * 	ExecuteUpdade ser igual a zero.
	 * 
	 * */
	public boolean modificar (Gerente gerente){
		
		if(gerente == null) return false;
		
		query = "UPDATE gerente "
			+ "SET nome = '"+gerente.getNome().toUpperCase()+
					"', senha = '"+gerente.getSenha()+"'"
			+ " WHERE idgerente = "+gerente.getId()+";";
		
		return (pers.ExecuteUpdate(query) > 0);
	}
	
	
	/*		
	 * 		Remover
	 * 
	 * 		Recebe um objeto gerente e o remover no banco de dados, segundo o id deste objeto.
	 * 
	 * 		Retorna verdadeiro caso a remoção ocorrer de maneira correta, ou seja, 
	 * 	o resultado do ExecuteUpdate ser diferente de 0.
	 * 
	 * 		Retorna falso caso o objeto passado seja nulo ou o resultado do 
	 * 	ExecuteUpdade ser igual a zero.
	 * 
	 * */
	public boolean remover (Gerente gerente){
		
		if(gerente == null) return false;
		
		query = "DELETE FROM gerente"
				+ " WHERE idgerente = "+gerente.getId()+";";
		
		return (pers.ExecuteUpdate(query) > 0);
		
	}
	/*
	 * 		INSERIR	
	 * 		
	 * 
	 * 		Recebe um ArrayList de Gerentes e os insere na base de dados.
	 * 
	 * 		Retorna verdadeiro se todos os elementos deste array forem inseridos 
	 * 	na base corretamente.
	 * 
	 * 		Retorna falso caso a lista passada seja nula ou com tamanho 0. Caso 
	 * 	algum item não seja inserido de maneira correta o metodo retorna nulo.
	 * 
	 * */
	@Override
	public boolean inserir(List<Gerente> lista) {
		
		
		if(lista == null || lista.size() == 0) return false;
		
		for(Gerente gerente : lista){
			
			if(!this.inserir(gerente)) return false;
			
		}
		
		return true;
	}
}
