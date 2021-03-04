package businessService.CRUDAprendizagem.pesquisarReferencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Questao;
import modelObjects.Referencia;

public class PesquisarReferencia implements IPesqReferencia, IPersistencia{
	
	private String query;

	@Override
	public List<Referencia> pesquisarTodos() {
		this.query = "SELECT * FROM referencia;";
		
		ResultSet rs = pers.ExecuteQuery(query);
		
		List<Referencia> result = new ArrayList<>();
		Referencia referencia;
		
		try{
			while(rs.next()){
				referencia = new Referencia();
				
				referencia.setAutor(rs.getString("autor"));
//				Calendar cal = new GregorianCalendar();
//		        cal.setTime(rs.getDate("ano"));
//				referencia.setData(cal);
				referencia.setEditora(rs.getString("editora"));
				referencia.setIdReferencia(rs.getInt("idreferencia"));
				referencia.setTitulo(rs.getString("titulo"));
				
				result.add(referencia);
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return result;
		
	}

	@Override
	public Referencia pesquisar(int id) {
		if(id < 1) return null;
		
		this.query = "SELECT * FROM referencia WHERE idreferencia = "+id+";";
		
		return this.GerarReferencia(pers.ExecuteQuery(query));
	}
	
	@Override
	public Referencia pesquisar(Questao questao){
		
		if (questao == null || questao.getReferencia() == null 
				|| questao.getReferencia().getIdReferencia() < 1) return null;
		
		this.query = "select * from referencia where idreferencia = "
		+questao.getReferencia().getIdReferencia()+" ;";
		
		return this.GerarReferencia(pers.ExecuteQuery(query));
	}
	@Override
	public boolean verificarExistencia(Referencia ref){
		
		if(ref == null || ref.getAutor().isEmpty() || 
				ref.getTitulo().isEmpty()) return false;
		
		this.query = "select * from referencia where autor = '"+
				ref.getAutor().toUpperCase()+"' and titulo = '"+
				ref.getTitulo().toUpperCase()+"';";
		
		if (this.GerarReferencia(pers.ExecuteQuery(query)) == null)return false;
		
		return true;
	}
	
	
	private Referencia GerarReferencia(ResultSet rs){
		
		Referencia referencia = new Referencia();
		if (rs== null) return null;
		
		try{
			if(rs.next()){
				referencia.setAutor(rs.getString("autor"));
//				Calendar cal = new GregorianCalendar();
//		        cal.setTime(rs.getDate("ano"));
//				referencia.setData(cal);
				referencia.setEditora(rs.getString("editora"));
				referencia.setIdReferencia(rs.getInt("idreferencia"));
				referencia.setTitulo(rs.getString("titulo"));
				
				return referencia;
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return null;
		
	}	

}
