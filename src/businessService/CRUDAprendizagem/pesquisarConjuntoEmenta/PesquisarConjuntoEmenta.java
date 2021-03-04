package businessService.CRUDAprendizagem.pesquisarConjuntoEmenta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Disciplina;
import modelObjects.Ementa;


/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 *  - Que oferece o serviço de persistencia 		(IPersistencia);
 *  
 *  - Oferecem os serviços de pesquisa:
 *  		-  de disciplina 				(IPesquisarDisciplina)
 *  
 *  Requerida: IPesqConjEmenta - Define o serviço de pesquisar conjunto de turma.
 *  
 * */
public class PesquisarConjuntoEmenta implements IPesqConjEmenta, IPersistencia,
IPesquisarDisciplina {
	
	private String query;
	
	
	/*
	 * 		Pesquisar
	 * 
	 * Pesquisa uma lista de ementa
	 * 
	 * retorna falso se:
	 * 		- a lista passada é nula ou vazia,
	 * 		- algum elemento da lista possuir id de ementa ou disciplina menor que 0
	 * 		- não for encontrada emenda na base de dados para aquele objeto ementa.
	 * 
	 * retorna verdadeiro se para todo elemento presente na lista, existir um corres
	 * pondente na base de dados
	 * 
	 * */
	public boolean pesquisar(ArrayList<Ementa> ementas){
		Iterator<Ementa> it = ementas.iterator();
		Ementa aux;
		
		if(ementas == null || ementas.isEmpty()) return false;
		
		while(it.hasNext()){
			
			aux = it.next();
			
			if(aux.getIdEmenta()<0 || aux.getDisciplina().getIdDisicplina() < 0) return false;
			
			this.query = "select idementa from ementa where idementa = "+aux.getIdEmenta()
					+" and iddisciplina ="+aux.getDisciplina().getIdDisicplina()+";";
			
			
			try {
				if(!pers.ExecuteQuery(query).first()) return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		return true;
		
	}
	
	/*
	 * 		Pesquisar
	 * 
	 * 	Pesquisa as ementas de determinada disciplina.
	 * 
	 * Retorna uma lista de ementas que correspondem a disciplina informada.
	 * 
	 * Retorna nulo se a disciplina passada for nula ou se esta disciplina não
	 * está presente na base de dados 
	 * */
	public ArrayList<Ementa> pesquisar (Disciplina disciplina){
		
		if(disciplina == null) return null;
		
		if(pesqDisc.pesquisar(disciplina.getIdDisicplina())== null) return null;
		
		this.query = "select * from ementa where iddisciplina = "+disciplina.getIdDisicplina()+";";
		
		return this.transformar(pers.ExecuteQuery(query), disciplina);
		
		
	}
	
	/*
	 * 		Pesquisar Todos
	 * 
	 * Pesquisa todas as instancias de ementa da base de dados
	 * 
	 * Retorna uma lista com todas as ementas da base
	 * 
	 * */
	public ArrayList<Ementa> pesquisarTodos (){
		
		this.query = "select * from ementa ;";
		
		return this.transformar(pers.ExecuteQuery(query));
		
		
	}
	
	//transforma o result set em um objeto ementa
	public ArrayList<Ementa> transformar (ResultSet rs, Disciplina disciplina){
		
		Ementa ementa;
		ArrayList<Ementa> ementas = new ArrayList<>();
		
		if(rs == null) return null;
		
		
		
		try{
			
			while(rs.next()){
				
				ementa = new Ementa();
				
				ementa.setDisciplina(disciplina);
				ementa.setIdEmenta(rs.getInt("idementa"));
				ementa.setNome(rs.getString("nome"));
				
				ementas.add(ementa);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return ementas;
	}
	
	//transforma o result set em um objeto ementa
	public ArrayList<Ementa> transformar (ResultSet rs){
		
		Ementa ementa;
		ArrayList<Ementa> ementas = new ArrayList<>();
		
		if(rs == null) return null;
		
		
		
		try{
			
			while(rs.next()){
				
				ementa = new Ementa();
				
				ementa.setDisciplina(pesqDisc.pesquisar(rs.getInt("iddisciplina")));
				ementa.setIdEmenta(rs.getInt("idementa"));
				ementa.setNome(rs.getString("nome"));
				
				ementas.add(ementa);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return ementas;
	}

}
