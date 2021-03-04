package businessService.CRUDAprendizagem.pesquisarConteudo;

import java.sql.ResultSet;
import java.sql.SQLException;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Conteudo;
import modelObjects.Disciplina;
import modelObjects.Ementa;



public class PesquisarConteudo implements IPesqConteudo, IPersistencia {
	
	private String query;
	
	public Conteudo pesquisar (String nome){
		
		if(nome == null || nome.isEmpty()) return null;
		
		this.query = "select * from conteudo where nome = '"+nome.toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	public Conteudo pesquisar (Conteudo conteudo){
		
		if(conteudo == null || conteudo.getIdConteudo() < 0) return null;
		
		this.query = "select * from conteudo where idconteudo= "+conteudo.getIdConteudo()+";";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	public Conteudo transformar (ResultSet rs){
		Conteudo conteudo;
		Ementa ementa;
		Disciplina dis;
		
		if(rs == null) return null;
		
		try{
			if(rs.next()){
				
				conteudo = new Conteudo();
				ementa = new Ementa();
				dis = new Disciplina();
				
				dis.setIdDisicplina(rs.getInt("iddisciplina"));
				ementa.setIdEmenta(rs.getInt("idementa"));
				ementa.setDisciplina(dis);				
				conteudo.setEmenta(ementa);
				conteudo.setNome(rs.getString("nome"));
				conteudo.setDescricao(rs.getString("descricao"));
				conteudo.setIdConteudo(rs.getInt("idconteudo"));
				
				return conteudo;
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
		}
		
		return null;
	}

}
