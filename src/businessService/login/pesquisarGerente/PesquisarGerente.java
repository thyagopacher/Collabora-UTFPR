package businessService.login.pesquisarGerente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Gerente;

public class PesquisarGerente implements IPesquisarGerente, IPersistencia{
	
	private String query;
	
	public Gerente pesquisar(int id){
		
		this.query = "select * from gerente where idgerente = "+id+";";
		
		return this.transformar(pers.ExecuteQuery(query));
	}
	
	public ArrayList<Gerente> pesquisarTodos(){
		
		this.query = "select * from gerente;";
		
		return this.transformarConjunto(pers.ExecuteQuery(query));
	}
	
	public Gerente pesquisar(Gerente gerente){
		
		if(gerente == null) return null;
		
		this.query = "select * from gerente where nome = '"+gerente.getNome().toUpperCase()+
				"' and senha = '"+gerente.getSenha()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}
	
	public ArrayList<Gerente> transformarConjunto(ResultSet rs){
		
		if(rs == null) return null;
		
		Gerente gerente;
		ArrayList<Gerente> conjunto = new ArrayList<Gerente>();
		
		try{
				
			while(rs.next()){
				gerente = new Gerente();
				
				gerente.setNome(rs.getString("nome"));
				gerente.setSenha(rs.getString("senha"));
				gerente.setId(rs.getInt("idgerente"));
				
				conjunto.add(gerente);
			}
				
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return conjunto;
		
		
	}
	
	public Gerente transformar (ResultSet rs){
		
		if(rs == null) return null;
		
		Gerente gerente = new Gerente();
		
		try{
			if(rs.next()){
				gerente.setNome(rs.getString("nome"));
				gerente.setSenha(rs.getString("senha"));
				return gerente;
			}
		}
		catch(SQLException e){
				e.printStackTrace();
		}
		
		return null;
		
	}
		
}
