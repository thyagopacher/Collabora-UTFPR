package businessService.login.manterAluno;

import modelObjects.Aluno;

import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarAluno.IPesquisarAluno;

public class ManterAluno implements IMantAluno, IPersistencia, IPesquisarAluno{
	
	private String query;
	
	public boolean inserir (Aluno aluno){
		
		if(aluno == null || aluno.getRegistro() == null ||
				aluno.getNome() == null || aluno.getSenha() == null ||
				aluno.getRegistro().isEmpty()|| aluno.getNome().isEmpty()||
				aluno.getSenha().isEmpty()) return false;
		
		if(pesqAluno.pesquisar(aluno.getRegistro()) == null){
			
			this.query = "Insert into aluno (nome, senha, registroacademico) "
					+ "values ('"+aluno.getNome().toUpperCase()+"',"
							+ " '"+aluno.getSenha()+"',"
							+ " '"+aluno.getRegistro().toUpperCase()+"');";
			
			return(pers.ExecuteUpdate(query) > 0);		
			
		}
		return false;
				
	}
	
	public boolean modificar (Aluno aluno){
		
		if(aluno == null) return false;
		
		this.query = "update aluno set nome= '"+aluno.getNome().toUpperCase()+"',"
		+ " senha ='"+aluno.getSenha()+"',"
		+ " registroacademico= '"+aluno.getRegistro().toUpperCase()+"'"
		+ " where idaluno = "+aluno.getId()+";";
		
		//System.out.println(query);
			
		return (pers.ExecuteUpdate(query)>0);
		
	}
	
	public boolean remover (Aluno aluno){
		
		if(aluno == null) return false;
		
		this.query = "delete from aluno where idaluno = "+aluno.getId()+";";
		
		return (pers.ExecuteUpdate(query)>0);
		
	}

	@Override
	public boolean inserir(List<Aluno> lista) {
		
		if(lista == null || lista.size() == 0) return false;
		
		for(Aluno aluno : lista){
			
			if(!this.inserir(aluno)) return false;
		}
		
		return true;
	}

}
