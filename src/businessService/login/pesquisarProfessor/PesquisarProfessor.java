package businessService.login.pesquisarProfessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Professor;

public class PesquisarProfessor implements IPersistencia, IPesqProfessor{
	
	private String query;
	
	public Professor pesquisar(String registro){
		
		if(registro.isEmpty()) return null;
		
		this.query = "select * from professor where registroprofessor = '"+registro.toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}
	
	public Professor pesquisarPorId (int idProfessor){
		
		if (idProfessor < 1) return null;
		
		this.query = "select * from professor where idprofessor = "+idProfessor+";";
		
		return this.transformar(pers.ExecuteQuery(query));
	}
	
	public Professor transformar(ResultSet rs){
	
	if(rs == null) return null;
		
	Professor professor;
	
	try{
		if(rs.next()){
			
			professor = new Professor();
			
			professor.setNome(rs.getString("nome"));
			professor.setSenha(rs.getString("senha"));
			professor.setRegistro(rs.getString("registroprofessor"));
			professor.setId(rs.getInt("idprofessor"));
			return professor;
		}
	}
	catch(SQLException e){
			e.printStackTrace();
	}
	
	return null;
	}

	@Override
	public Professor pesquisar(Professor professor) {
	
		if(professor == null || professor.getNome().isEmpty() 
				|| professor.getSenha().isEmpty()) return null;
		
		this.query = "select * from professor where nome = '"+professor.getNome().toUpperCase()+
				"' and senha = '"+professor.getSenha().toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}

	@Override
	public ArrayList<Professor> pequisarTodos() {
		ResultSet rs;
		
		this.query = "select * from professor";
		
		rs  = pers.ExecuteQuery(query);
		
		if(rs == null) return null;
		
		ArrayList<Professor> professores = new ArrayList<>();
		Professor professor;
		
		try{
				
			while(rs.next()){
				professor = new Professor();
				
				professor.setNome(rs.getString("nome"));
				professor.setSenha(rs.getString("senha"));
				professor.setRegistro(rs.getString("registroprofessor"));
				professor.setId(rs.getInt("idprofessor"));
				
				professores.add(professor);
				
			}
				
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return professores;
		
	}

}
