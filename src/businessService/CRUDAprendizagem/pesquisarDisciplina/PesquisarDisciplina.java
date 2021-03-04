package businessService.CRUDAprendizagem.pesquisarDisciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Disciplina;



public class PesquisarDisciplina implements IPesqDisciplina, IPersistencia{
	
	private String query;
	
	public Disciplina pesquisar(int id){
		
		if (id<1) return null;
		
		this.query = "select * from disciplina where iddisciplina = "+id+";";
		
		return (this.transformar(pers.ExecuteQuery(query)));
	}
	
	public Disciplina pesquisar(String nome, String codigo){
		
		if (nome.isEmpty() || codigo.isEmpty()) return null;
		
		this.query = "select * from disciplina where nome = '"+nome.toUpperCase()
					+"' and codigo = '"+codigo.toUpperCase()+"';";
		
		return (this.transformar(pers.ExecuteQuery(query)));
		
	}
	
	public Disciplina transformar(ResultSet rs){
		
		if (rs == null) return null;
		
		Disciplina disciplina;
		
		try{
			if(rs.next()){
				
				disciplina = new Disciplina();
				
				disciplina.setCodigo(rs.getString("codigo"));
				disciplina.setNome(rs.getString("nome"));
				disciplina.setIdDisicplina(rs.getInt("iddisciplina"));
				
				return disciplina;
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
		}
		
		return null;
		}

	@Override
	public List<Disciplina> pesquisaTodos() {
		
		this.query = "select * from disciplina;";
		
		return this.transformarConjunto(pers.ExecuteQuery(query));
	}

	@Override
	public List<Disciplina> transformarConjunto(ResultSet rs) {
		if (rs == null) return null;
		
		List<Disciplina> conjunto = new ArrayList<>();
		Disciplina disciplina;
		
		try{
			while(rs.next()){
				
				disciplina = new Disciplina();
				
				disciplina.setCodigo(rs.getString("codigo"));
				disciplina.setNome(rs.getString("nome"));
				disciplina.setIdDisicplina(rs.getInt("iddisciplina"));
				
				conjunto.add(disciplina);
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		if(conjunto.isEmpty())return null;
		else return conjunto;
		}
	}		
