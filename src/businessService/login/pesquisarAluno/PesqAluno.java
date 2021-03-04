package businessService.login.pesquisarAluno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelObjects.Aluno;

import businessService.dbConnection.persistencia.IPersistencia;

public class PesqAluno implements IpesqAluno, IPersistencia{
	
	private String query;
	
	public List<Aluno> pesquisarNome(String Nome){
		
		if (Nome == null || Nome.isEmpty()) return null;
		
		query = "select * from aluno where nome like '"+Nome.toUpperCase()+"%';";
		
		return this.transformarConjunto(pers.ExecuteQuery(query));
		
	}
	
	public Aluno pesquisar(String registro){
		
		if (registro == null || registro.isEmpty()) return null;
		
		query = "select * from aluno where registroacademico = '"+registro.toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	public Aluno transformar(ResultSet rs){
		
		if(rs == null) return null;
			
		Aluno aluno;
		
		try{
			if(rs.next()){
				aluno = new Aluno();
				
				aluno.setNome(rs.getString("nome"));
				aluno.setSenha(rs.getString("senha"));
				aluno.setRegistro(rs.getString("registroacademico"));
				aluno.setId(rs.getInt("idaluno"));
				return aluno;
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return null;
	}

	@Override
	public Aluno pesquisar(Aluno aluno) {
		
		if (aluno == null || aluno.getNome() == null||aluno.getNome().isEmpty() 
				||aluno.getSenha()==null|| aluno.getSenha().isEmpty()) return null;
		
		query = "select * from aluno where nome = '"+aluno.getNome()+
				"' and senha = '"+aluno.getSenha()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}

	@Override
	public List<Aluno> transformarConjunto(ResultSet rs) {
		if(rs == null) return null;
		
		List<Aluno>	alunos = new ArrayList<>();	
		Aluno aluno;
		
		try{
			while(rs.next()){
				aluno = new Aluno();
				
				aluno.setNome(rs.getString("nome"));
				aluno.setSenha(rs.getString("senha"));
				aluno.setRegistro(rs.getString("registroacademico"));
				aluno.setId(rs.getInt("idaluno"));
				
				alunos.add(aluno);
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return alunos;
	}

	@Override
	public Aluno pesquisarPorId(String id) {
		
		if (id.isEmpty()) return null;
		
		query = "select * from aluno where idaluno = '"+id+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}

}
