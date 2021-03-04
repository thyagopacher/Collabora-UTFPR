package businessService.CRUDAprendizagem.manterQuestao;

import businessService.CRUDAprendizagem.pesquisaEmenta.IPesquisarEmenta;
import businessService.CRUDAprendizagem.pesquisarConteudo.IPesquisarConteudo;
import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.CRUDAprendizagem.pesquisarQuestao.IPesquisarQuestao;
import businessService.CRUDAprendizagem.pesquisarReferencia.IPesquisarReferencia;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Proposicao;
import modelObjects.Questao;

public class ManterQuestao implements IMantQuestao, IPersistencia, IPesquisarReferencia, IPesquisarDisciplina, 
IPesquisarEmenta, IPesquisarConteudo, IPesquisarQuestao{
	private String query;

	@Override
	public boolean insert(Questao quest) {
		
		if(!this.lavidar(quest)) return false;
		
		this.query = "INSERT INTO questao (dificuldade, imagem, enunciado, peso, idreferencia)"
				+ " VALUES ('"+quest.getDificuldade().toUpperCase()+"','"+quest.getImagem().toUpperCase()+"','"+
				quest.getEnunciado()+"',"+quest.getPeso()+","+quest.getReferencia().getIdReferencia()+");";
		
		if(pers.ExecuteUpdate(query) < 1) return false;
		
		int aux = pesqQuestao.pesquisarIdQuestao(quest);
		
		quest.setIdQuestao(aux);
		
		this.query = "INSERT INTO conteudoquestao (idconteudo, idquestao, idementa, iddisciplina)"
				+ " VALUES ("+quest.getConteudo().getIdConteudo()+","+quest.getIdQuestao()+","
				+quest.getConteudo().getEmenta().getIdEmenta()+","+quest.getConteudo().getEmenta().getDisciplina().getIdDisicplina()+");";
		
		if( pers.ExecuteUpdate(query) < 1) return false;
		
		return this.inserirProposicoes(quest);
	}

	@Override
	public boolean update(Questao quest) {
		
		if(!this.lavidar(quest)) return false;
		
		if(!this.modificarProposicoes(quest)) return false;
		
		this.query= "UPDATE questao SET dificuldade = '"+quest.getDificuldade().toUpperCase()+"',"
				+ " imagem ='"+quest.getImagem().toUpperCase()+"', enunciado='"+quest.getEnunciado()+
				"', peso = "+quest.getPeso()+", idreferencia = "+quest.getReferencia().getIdReferencia()
				+" WHERE idquestao = "+quest.getIdQuestao()+";";
		
//		System.out.println(query);return false;
		
		if(pers.ExecuteUpdate(query) < 1) return false;
		
		this.query = "UPDATE conteudoquestao SET idconteudo="+quest.getConteudo().getIdConteudo()
				+", idementa = "+quest.getConteudo().getEmenta().getIdEmenta()+", iddisciplina = "+
				quest.getConteudo().getEmenta().getDisciplina().getIdDisicplina()+
				" WHERE idquestao = "+quest.getIdQuestao()+";";
		
		return pers.ExecuteUpdate(query) > 0;
	}

	@Override
	public boolean delete(Questao quest) {
		
		if(!this.lavidar(quest)) return false;
		
		if(!this.removerProposicoes(quest)) return false;
		
		this.query = "DELETE from conteudoquestao WHERE idquestao = "+quest.getIdQuestao()+";";
		
		if(pers.ExecuteUpdate(query) < 1) return false;
		
		
		this.query = "DELETE from questao WHERE idquestao = "+quest.getIdQuestao()+";";
		
		return pers.ExecuteUpdate(query) > 0;
		
	}
	
	private boolean inserirProposicoes(Questao quest){
		
		if( quest == null ||quest.getProposicoes() == null || 
				quest.getProposicoes().isEmpty() || quest.getIdQuestao() < 1) return false;
		
		for(Proposicao prop : quest.getProposicoes()){
			
			this.query = "INSERT INTO proposicao (idquestao, enunciado, correta, imagem) VALUES("
					+ quest.getIdQuestao()+",'"+prop.getEnunciado()+"',"+prop.isCorreta()+",'"
					+prop.getImagem().toUpperCase()+"');";
			
			if(pers.ExecuteUpdate(query) < 1) return false;
			
		}
		
		return true;
	}
	
	private boolean removerProposicoes(Questao quest){
		
		this.query = "DELETE FROM proposicao WHERE idquestao = "+quest.getIdQuestao()+";";
		
		return pers.ExecuteUpdate(query) > 0;
	}
	
	private boolean modificarProposicoes(Questao quest){
		
		if(this.removerProposicoes(quest)){
			return this.inserirProposicoes(quest);
		}
		
		return false;
		
	}
	
	private boolean lavidar(Questao quest){
		
		if(quest == null || quest.getConteudo() == null || quest.getProposicoes() == null
				||quest.getProposicoes().isEmpty()|| quest.getReferencia() == null || 
				quest.getConteudo().getEmenta() == null 
				|| quest.getConteudo().getEmenta().getDisciplina() == null||
				quest.getConteudo().getEmenta().getIdEmenta() < 1|| 
				quest.getConteudo().getEmenta().getDisciplina().getIdDisicplina() < 1) return false;
		
		if(pesqConteudo.pesquisar(quest.getConteudo()) == null) return false;
		
		if(pesqEmenta.pesquisar(quest.getConteudo().getEmenta().getIdEmenta()) == null) return false;
		
		if(pesqDisc.pesquisar(quest.getConteudo().getEmenta().getDisciplina().getIdDisicplina())== null) return false;
		
		if(pesqRef.pesquisar(quest.getReferencia().getIdReferencia())== null) return false;
		
		return true;
		
	}

}
