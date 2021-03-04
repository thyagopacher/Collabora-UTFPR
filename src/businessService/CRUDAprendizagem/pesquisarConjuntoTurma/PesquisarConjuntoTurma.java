package businessService.CRUDAprendizagem.pesquisarConjuntoTurma;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Turma;


public class PesquisarConjuntoTurma implements IPesqConjTurma, IPersistencia {
	
	public boolean pesquisar(ArrayList<Turma> turmas){
		
		String query;
		int aux = 0;
		ResultSet rs;
		
		if(turmas == null) return false;
		
		while(aux<turmas.size()){
			
			query = "select idturma from turma where idturma = "
					+turmas.get(aux).getId()+";";
			
			rs = pers.ExecuteQuery(query);
			
			try {
				if(!rs.first()) return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			aux++;
		}
		
		return true;
		
	}

}
