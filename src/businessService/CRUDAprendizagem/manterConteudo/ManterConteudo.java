package businessService.CRUDAprendizagem.manterConteudo;

import businessService.CRUDAprendizagem.pesquisaEmenta.IPesquisarEmenta;
import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Conteudo;
import businessService.CRUDAprendizagem.pesquisarConteudo.IPesquisarConteudo;

public class ManterConteudo implements ImantConteudo, IPesquisarConteudo,
IPersistencia, IPesquisarEmenta, IPesquisarDisciplina{
	
	private String query;
	
	public boolean inserir (Conteudo conteudo){
		
		
		System.out.println(conteudo);
		System.out.println(conteudo.getIdConteudo());
		System.out.println(conteudo.getEmenta());
		System.out.println(conteudo.getEmenta().getIdEmenta());
		System.out.println(conteudo.getEmenta().getDisciplina());
		System.out.println(conteudo.getEmenta().getDisciplina().getIdDisicplina());
		
		if(conteudo == null || conteudo.getEmenta().getIdEmenta()<0
				|| conteudo.getEmenta().getDisciplina().getIdDisicplina() < 0) return false;
		
		
		if(pesqEmenta.pesquisar(conteudo.getEmenta().getIdEmenta()) == null) return false;
		
		if(pesqDisc.pesquisar(conteudo.getEmenta().getDisciplina().getIdDisicplina())== null) 
			return false;
		
		if(pesqConteudo.pesquisar(conteudo.getNome()) != null) return false;
		
		this.query = "insert into conteudo(iddisciplina, idementa, nome, descricao) values("+
				conteudo.getEmenta().getDisciplina().getIdDisicplina()+","
				+conteudo.getEmenta().getIdEmenta()+",'"
				+conteudo.getNome().toUpperCase()+"','"
				+conteudo.getDescricao().toUpperCase()+"');";
	
		
		return pers.ExecuteUpdate(query)>0;
		
		
	}
	
	public boolean modificar (Conteudo conteudo){
		
		
		if(conteudo == null || conteudo.getIdConteudo()<0 || conteudo.getEmenta().getIdEmenta()<0
				|| conteudo.getEmenta().getDisciplina().getIdDisicplina() < 0) return false;
		
		System.out.println(1);
		if(pesqEmenta.pesquisar(conteudo.getEmenta().getIdEmenta()) == null) return false;
		
		System.out.println(2);
		if(pesqDisc.pesquisar(conteudo.getEmenta().getDisciplina().getIdDisicplina())== null) return false;
		
		System.out.println(3);
		if(pesqConteudo.pesquisar(conteudo) == null) return false;
		
		System.out.println(4);
		query = "update conteudo set nome = '"+conteudo.getNome().toUpperCase()+
				"', descricao = '"+conteudo.getDescricao().toUpperCase()+"',"+
				" idementa = "+conteudo.getEmenta().getIdEmenta()+","+
				" iddisciplina = "+conteudo.getEmenta().getDisciplina().getIdDisicplina()+
				" where idconteudo = "+conteudo.getIdConteudo()+";";
		
		return pers.ExecuteUpdate(query)>0;
		
	}
	
	public boolean remover (Conteudo conteudo){
		
		if(conteudo == null || conteudo.getIdConteudo()<0 || conteudo.getEmenta().getIdEmenta()<0
				|| conteudo.getEmenta().getDisciplina().getIdDisicplina() < 0) return false;
		
		if(pesqConteudo.pesquisar(conteudo.getNome()) == null) return false;
		
		query = "delete from conteudo where idconteudo = "+conteudo.getIdConteudo()+";";
		
		return pers.ExecuteUpdate(query)>0;
		
	}
	

}
