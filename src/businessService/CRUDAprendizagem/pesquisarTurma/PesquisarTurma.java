package businessService.CRUDAprendizagem.pesquisarTurma;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.CRUDAprendizagem.pesquisarAlunosPorTurma.IPesquisarAlunosPorTurma;
import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarProfessor.IPesquisarProfessor;
import modelObjects.Disciplina;
import modelObjects.Turma;


/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 *  - Que oferece o serviço de persistencia 		(IPersistencia);
 *  
 *  - Oferecem os serviços de pesquisa:
 *  		-  de professor 				(IPesquisarProfessor)
 *  		-  de disciplina 				(IPesquisarDisciplina)
 *  		- alunos de determinada turma 	(IPesquisarAlunosPorTurma);
 *  
 *  Requerida: IPesqTurma - Define o serviço de pesuisa de uma turma.
 *  
 * */
public class PesquisarTurma implements IPesqTurma, IPersistencia, IPesquisarProfessor,
IPesquisarDisciplina, IPesquisarAlunosPorTurma{
	
	private String query;
	
	/*
	 * 		Pesquisar
	 * 
	 * 	Pesquisa segundo o código desta disciplina
	 * 
	 * 		Retorna nulo se o código passado for nulo ou vazio. Também 
	 * 	retorna nulo se após a execução do sql não houver retorno no result set
	 * 
	 * 		Retorna uma turma caso encontre a turma desejada.
	 * 
	 * */
	public Turma pesquisar(String codigo){
		
		if(codigo == null || codigo.isEmpty()) return null;
		
		this.query = "select * from turma where codigo = '"+codigo.toUpperCase()+"';";
		
		return this.transformar(pers.ExecuteQuery(this.query));
	}
	
	/*
	 * 		Pesquisar
	 * 
	 * 	Pesquisa segundo o ID desta disciplina
	 * 
	 * 		Retorna nulo se o id passado for menor que 0 ou 
	 *  se após a execução do sql não houver retorno no result set
	 * 
	 * 		Retorna uma turma caso encontre a turma desejada.
	 * 
	 * */
	public Turma pesquisarPorId(String id){
		
		if(id.isEmpty()) return null;
		
		this.query = "select * from turma where idturma = "+id+";";
		
		return this.transformar(pers.ExecuteQuery(this.query));
	}
	
	
	public List<Turma> pesquisarByDisciplina(Disciplina disciplina){
		
		List<Turma> turmas = new ArrayList<>();
		
		if(disciplina == null || disciplina.getIdDisicplina() < 1)
			return turmas;
		
		this.query = "select * from turma where iddisciplina = "+disciplina.getIdDisicplina()+";";
		
		 ResultSet rs = pers.ExecuteQuery(this.query);
		
		 Turma aux = null;
			
			try{
				while(rs.next()){
					
					aux = new Turma();
					
					aux.setCodigo(rs.getString("codigo"));
					aux.setId(rs.getInt("idturma"));
					
					aux.setDisciplina(pesqDisc.pesquisar(disciplina.getIdDisicplina()));
					aux.setProfessor(pesqProf.pesquisarPorId(rs.getInt("idprofessor")));
					aux.setAluno(pesqAlunosPorTurma.pequisarPorTurma(aux));
					
					turmas.add(aux);
					
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		
		return turmas;
	}
	
	/*
	 * 		Transformar
	 * 
	 * 	Recebe um resultSet e transforma em um objeto turma
	 * 
	 * 	retorna nulo se o result set for vazio, caso contrario retorna 
	 * o objeto turma com seus respectivos valores.
	 * 
	 * 
	 * */
	public Turma transformar(ResultSet rs){
		
		Turma turma = null;
		
		if (rs == null) return null;
		
		try{
			if(rs.next()){
				
				turma = new Turma();
				
				turma.setCodigo(rs.getString("codigo"));
				turma.setId(rs.getInt("idturma"));
				
				turma.setDisciplina(pesqDisc.pesquisar(rs.getInt("iddisciplina")));
				turma.setProfessor(pesqProf.pesquisarPorId(rs.getInt("idprofessor")));
				turma.setAluno(pesqAlunosPorTurma.pequisarPorTurma(turma));
				
				return turma;
			}
		}
		catch(SQLException e){
				e.printStackTrace();
		}
		
		return null;
				
	}
	
	
	/*
	 * 		Pesquisar Todas
	 * 
	 * 	Pesquisa todas as instancias na relação turma
	 * 
	 * 
	 * 		Retorna uma lista de contendo todas as turmas.
	 * 
	 * */
	@Override
	public List<Turma> pesquisarTodas() {
		
		List<Turma> turmas = new ArrayList<>();
		
		this.query = "select * from turma ;";
		
		ResultSet rs = pers.ExecuteQuery(this.query);
		
		Turma aux = null;
		
		try{
			while(rs.next()){
				
				aux = new Turma();
				
				aux.setCodigo(rs.getString("codigo"));
				aux.setId(rs.getInt("idturma"));
				
				aux.setDisciplina(pesqDisc.pesquisar(rs.getInt("iddisciplina")));
				aux.setProfessor(pesqProf.pesquisarPorId(rs.getInt("idprofessor")));
				aux.setAluno(pesqAlunosPorTurma.pequisarPorTurma(aux));
				
				turmas.add(aux);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return turmas;
		
	}
}
