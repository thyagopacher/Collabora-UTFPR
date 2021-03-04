package businessService.login.manterProfessor;

import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarProfessor.IPesquisarProfessor;
import modelObjects.Professor;

public class ManterProfessor implements IPersistencia, IPesquisarProfessor, IMantProfessor {
	
	private String query;
	
	/** Insere um unico professor na base de dados
	 */
	public boolean inserir (Professor professor){
		
		if (professor == null) return false;
		
		if(pesqProf.pesquisar(professor.getRegistro())== null){
			
			query = "insert into professor (nome, senha, registroprofessor) "
					+ "values ('"+professor.getNome().toUpperCase()+"',"
					+ " '"+professor.getSenha()+"',"
					+ " '"+professor.getRegistro().toUpperCase()+"');";
			
			return  (pers.ExecuteUpdate(query) >0);
		}
		
		return false;
	}
	
	public boolean modificar (Professor professor){
		
		if (professor == null) return false;
		
		query = "update professor set nome = '"+professor.getNome().toUpperCase()+"',"
				+ " senha = '"+professor.getSenha()+"',"
				+ " registroprofessor = '"+professor.getRegistro().toUpperCase()+"' "
				+ " where idprofessor = "+professor.getId()+";";
		
		return (pers.ExecuteUpdate(query)>0);
		
	}
	
	public boolean remover (Professor professor){
		
		if (professor == null) return false;
		
		query = "delete from professor where idprofessor = "+professor.getId()+";";
		
		return (pers.ExecuteUpdate(query)>0);
		
	}
	
	public boolean inserir (List<Professor> professores){
		
		if(professores == null || professores.size() == 0) return false;
		
		for(Professor prof : professores){	
			
			if(!this.inserir(prof)) return false;
		}
		
		return true;
		
	}

}
