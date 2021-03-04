package businessService.CRUDAprendizagem.pesquisaEmenta;

import java.sql.ResultSet;
import java.sql.SQLException;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Disciplina;
import modelObjects.Ementa;
import businessService.CRUDAprendizagem.pesquisaEmenta.IPesqEmenta;



public class PesquisarEmenta implements IPesqEmenta, IPersistencia {
	
	private String query;
	
	public Ementa pesquisar(Ementa ementa){
		
		if(ementa == null || ementa.getNome().isEmpty()) return null;
		
		this.query = "select * from ementa where nome = '"+ementa.getNome().toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	public Ementa pesquisar(int idEmenta){
		
		if( idEmenta < 0) return null;
		
		this.query = "select * from ementa where idementa = '"+idEmenta+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
	}
	
	public Ementa transformar(ResultSet rs){
		Ementa ementa;
		Disciplina dis;
		
		if (rs == null) return null;
		
		try{
			if(rs.next()){
				
				ementa = new Ementa();
				dis = new Disciplina();
				
				dis.setIdDisicplina(rs.getInt("iddisciplina"));
				ementa.setDisciplina(dis);
				ementa.setIdEmenta(rs.getInt("idementa"));
				ementa.setNome(rs.getString("nome"));
				
				return ementa;
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return null;
		
	}

}
