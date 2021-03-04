package businessService.CRUDAprendizagem.pesquisarConjuntoConteudo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.CRUDAprendizagem.pesquisaEmenta.IPesquisarEmenta;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Conteudo;
import modelObjects.Ementa;


/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	-Oferece a o serviço de persistência
 * 
 * Requerida:
 * 	- Que oferece a definição do serviço de pesquisar um conjunto de conteudo (IPesqConjConteudo)
 * */
public class PesquisarConjuntoConteudo implements IPesqConjConteudo, IPersistencia, IPesquisarEmenta{
	
	private String query;
	
	/*
	 * 		Pesquisar
	 * 
	 * 	pesquisa a lista de conteúdos no banco de dados
	 * 
	 * retorna falso se a lista é nula ou se para algum item da mesma,
	 * não for possível encontrar referencia na base.
	 * 
	 * retorna verdadeiro se para todo elemento da lista for encontrada 
	 * referencia na base de dados.
	 * 
	 * */
	public boolean pesquisar (ArrayList<Conteudo> conteudos){
		int aux = 0;
		ResultSet rs;
		
		if(conteudos == null) return false;
		
		while(aux<conteudos.size()){
			
			query = "select idconteudo from conteudo where idconteudo = "
					+conteudos.get(aux).getIdConteudo()+";";
			
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
	
	/*
	 * 		pesquisar por ementa
	 * 
	 * 
	 * 	pesquisa todos os conteudos de determinada ementa.
	 * 
	 * retorna nulo caso:
	 * 		a ementa passada seja nula, com valor de id menor que 1
	 * 	ou houver algum problema na consulta com o banco.
	 * 
	 * retorna uma lista de conteudos da ementa passada se não ocorrer nenhum problema 
	 * na consulta
	 * */
	@Override
	public List<Conteudo> pesquisarPorEmenta(Ementa ementa) {
		
		if(ementa == null || ementa.getIdEmenta()<1)return null;
		
		query = "SELECT * from conteudo "
				+ "WHERE idementa = "+ementa.getIdEmenta()+";";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	private List<Conteudo> transformar(ResultSet rs){
		
		if(rs == null) return null;
		
		List<Conteudo> lista = new ArrayList<>();
		Conteudo aux = null;
		
		try {
			while(rs.next()){
				
				aux = new Conteudo();
				
				aux.setDescricao(rs.getString("descricao"));

				aux.setEmenta(pesqEmenta.pesquisar(rs.getInt("idementa")));
				
				aux.setIdConteudo(rs.getInt("idconteudo"));
				aux.setNome(rs.getString("nome"));
				
				lista.add(aux);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return lista;
		
		
	}
	
	/*
	 * 		pesquisar por Todos
	 * 
	 * 
	 * 	pesquisa todos os conteudos.
	 * 
	 * retorna nulo caso:
	 * 		houver algum problema na consulta com o banco.
	 * 
	 * retorna uma lista de conteudos da ementa passada se não ocorrer nenhum problema 
	 * na consulta
	 * */
	@Override
	public List<Conteudo> pesquisarTodos() {
		
		query = "SELECT * from conteudo ;";
		
		return this.transformar(pers.ExecuteQuery(query));
	}

}
