package businessService.CRUDAprendizagem.psequisarConjuntoAluno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarAluno.IPesquisarAluno;
import modelObjects.Aluno;

public class PesquisarConjuntoAluno implements IPesqConjAluno, IPersistencia, IPesquisarAluno {
	@Override
	public boolean pesquisar(List<Aluno> alunos){
		
		Iterator<Aluno> it = alunos.iterator();
		
		if(alunos == null || alunos.isEmpty()) return false;
		
		while(it.hasNext()){	
						
			if(pesqAluno.pesquisarPorId(it.next().getId()) == null) return false;
			
		}
		
		return true;
		
	}
	
	public ArrayList <Aluno> transformar(ResultSet rs){
		
		ArrayList <Aluno> alunos = new ArrayList <Aluno>();
		Aluno aluno;
		
		if (rs == null) return null;
		
		try{
			
			while(rs.next()){
				
				aluno = new Aluno();
				
				aluno.setId(rs.getInt("idaluno"));
				aluno.setNome(rs.getString("nome"));
				aluno.setRegistro(rs.getString("registroacademico"));
				aluno.setSenha(rs.getString("senha"));
				
				alunos.add(aluno);
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
		}
		
		return alunos;
	}

	@Override
	public List<Aluno> pesquisarTodos() {
		
		String query = "select * from aluno;";
		
		return this.transformar(pers.ExecuteQuery(query));
	}		

}
