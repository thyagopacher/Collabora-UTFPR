package businessService.CRUDAprendizagem.pesquisarQuestao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.CRUDAprendizagem.pesquisarReferencia.IPesquisarReferencia;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Conteudo;
import modelObjects.Proposicao;
import modelObjects.Questao;
import modelObjects.Referencia;


public class PesquisarQuestao implements IPesqQuestao, IPesquisarReferencia, IPersistencia{
	
	private String query;
	
	public Questao pesquisar(int id){
		
		if(id<0) return null;
		
		this.query = "select * from questao where idquestao = "+id+";";
		
		return this.transformar(pers.ExecuteQuery(query));
		
	}
	
	public Questao transformar(ResultSet rs){
		Questao questao;
		Referencia referencia;
		ArrayList<Proposicao> proposicoes = new ArrayList<Proposicao>();
		
		if (rs== null) return null;
		
		try{
			if(rs.next()){
				
				questao = new Questao();
				referencia = new Referencia();
				
				referencia.setIdReferencia(rs.getInt("idreferencia"));
				questao.setReferencia(referencia);
				questao.setDificuldade(rs.getString("dificuldade"));
				questao.setEnunciado(rs.getString("enunciado"));
				questao.setIdQuestao(rs.getInt("idquestao"));
				questao.setImagem(rs.getString("imagem"));
				questao.setPeso(rs.getDouble("peso"));
				
				referencia = pesqRef.pesquisar(questao);
				questao.setReferencia(referencia);
				
				proposicoes = this.pesquisarProposicoes(questao);
				questao.setProposicoes(proposicoes);
				
				return questao;
				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return null;
	}
	
	public int pesquisarIdQuestao(Questao quest){
		if (quest == null) return 0;
		
		this.query = "SELECT idquestao from questao "
				+ "WHERE dificuldade = '"+quest.getDificuldade().toUpperCase()+"' AND"
				+ " imagem = '"+quest.getImagem().toUpperCase()+"' AND"
				+" enunciado = '"+quest.getEnunciado()+"';";
		
		ResultSet rs = pers.ExecuteQuery(query);
		
		try {
			if(rs.next()){
				return rs.getInt("idquestao");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	
	public ArrayList<Proposicao> pesquisarProposicoes(Questao questao){
		
		if(questao == null || questao.getIdQuestao() < 1) return null;
		
		ArrayList<Proposicao> proposicoes = new ArrayList<Proposicao>();
		ResultSet rs;
		Proposicao proposicao;
		
		this.query = "select * from proposicao where idquestao = "
						+questao.getIdQuestao()+" ;";
		
		rs = pers.ExecuteQuery(query);
		
		if (rs== null) return null;
		
		try{
			while(rs.next()){
				
				proposicao = new Proposicao();
				
				proposicao.setCorreta(rs.getBoolean("correta"));
				proposicao.setEnunciado(rs.getString("enunciado"));
				proposicao.setIdProposicao(rs.getInt("idproposicao"));
				proposicao.setImagem(rs.getString("imagem"));
				proposicao.setNumero(rs.getString("numero"));
				
				proposicoes.add(proposicao);				
			}
		}
		catch(SQLException e){
				e.printStackTrace();
				return null;
		}
		
		return proposicoes;	
		
	}

	@Override
	public List<Questao> pesquisar(Conteudo con) {
		ResultSet rs;
		Questao questao;
		Referencia referencia;
		ArrayList<Proposicao> proposicoes = new ArrayList<>();
		List<Questao> questoes = new ArrayList<>();
		
		if(con == null || con.getIdConteudo() < 1 || con.getEmenta().getIdEmenta() < 1 
				|| con.getEmenta().getDisciplina().getIdDisicplina() < 1) return questoes;
		
		this.query = "SELECT q.idquestao, q.dificuldade, q.imagem, q.enunciado, q.peso, q.idreferencia "+
			"FROM questao as q, conteudoquestao as cq "+
			"WHERE cq.idconteudo = "+con.getIdConteudo()+" AND cq.idquestao = q.idquestao "+
			"AND cq.idementa = "+con.getEmenta().getIdEmenta()+" AND cq.iddisciplina = "+
			con.getEmenta().getDisciplina().getIdDisicplina()+";";
		
		rs = pers.ExecuteQuery(query);
		
		try{
			while(rs.next()){
				
				questao = new Questao();
				referencia = new Referencia();
				
				referencia.setIdReferencia(rs.getInt("idreferencia"));
				questao.setReferencia(referencia);
				questao.setDificuldade(rs.getString("dificuldade"));
				questao.setEnunciado(rs.getString("enunciado"));
				questao.setIdQuestao(rs.getInt("idquestao"));
				questao.setImagem(rs.getString("imagem"));
				questao.setPeso(rs.getDouble("peso"));
				
				referencia = pesqRef.pesquisar(questao);
				questao.setReferencia(referencia);
				
				proposicoes = this.pesquisarProposicoes(questao);
				questao.setProposicoes(proposicoes);
				
				questoes.add(questao);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return questoes;
		
	}

}
