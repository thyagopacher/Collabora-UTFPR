package businessService.CRUDAprendizagem.manterDisciplina;

import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Disciplina;

public class ManterDisciplina implements IMantDisciplina, IPersistencia, IPesquisarDisciplina{
	
	private String query;
	
	public boolean inserir (Disciplina disciplina){
		if (disciplina == null || disciplina.getNome().isEmpty() 
				|| disciplina.getCodigo().isEmpty() ) return false;
		
		if(pesqDisc.pesquisar(disciplina.getNome(), disciplina.getCodigo())!= null) return false;
		
		query = "insert into disciplina(nome, codigo) values ('"
		+disciplina.getNome().toUpperCase()+"', '"+disciplina.getCodigo().toUpperCase()+"');";
		
		return pers.ExecuteUpdate(query)>0;
	}
	
	public boolean modificar (Disciplina disciplina){
		
		if (disciplina == null || disciplina.getIdDisicplina() < 0) return false;
		
		if(pesqDisc.pesquisar(disciplina.getIdDisicplina())== null) return false;
		
		this.query = "update disciplina set nome = '"+disciplina.getNome().toUpperCase()
						+"', codigo = '"+disciplina.getCodigo().toUpperCase()+"' "
						+ "where iddisciplina = "+disciplina.getIdDisicplina()+";";
		
		return pers.ExecuteUpdate(query)>0;
	}
	
	public boolean remover (Disciplina disciplina){
		
		if (disciplina == null || disciplina.getIdDisicplina() < 0) return false;
		
		if(pesqDisc.pesquisar(disciplina.getIdDisicplina())== null) return false;
		
		this.query = "delete from disciplina "
				+ "where iddisciplina = "+disciplina.getIdDisicplina()+";";
		
		return pers.ExecuteUpdate(query)>0;
	}


}
