package businessService.CRUDAprendizagem.manterEmenta;

import businessService.CRUDAprendizagem.pesquisaEmenta.IPesquisarEmenta;
import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Ementa;

public class ManterEmenta implements IMantEmenta, IPersistencia, IPesquisarDisciplina,
IPesquisarEmenta {
	
	private String query;
		
	public boolean inserir (Ementa ementa){
		
		if(ementa == null || ementa.getNome().isEmpty()) return false;
		
		if(pesqDisc.pesquisar(ementa.getDisciplina().getIdDisicplina()) == null) return false;
		
		if(pesqEmenta.pesquisar(ementa)!= null) return false;
		
		this.query = "insert into ementa(nome, iddisciplina) values ('"+
		ementa.getNome().toUpperCase()+"',"+ementa.getDisciplina().getIdDisicplina()+");";
		
		return pers.ExecuteUpdate(query)>0;
		
	}
	
	public boolean modificar (Ementa ementa){
		
		if(ementa == null || ementa.getNome().isEmpty()) return false;
		
		if(pesqDisc.pesquisar(ementa.getDisciplina().getIdDisicplina()) == null) return false;
		
		if(pesqEmenta.pesquisar(ementa.getIdEmenta())== null) return false;
		
		this.query = "update ementa set nome = '"+ementa.getNome().toUpperCase()
				+"', iddisciplina = "+ementa.getDisciplina().getIdDisicplina()+
				" where idementa = "+ementa.getIdEmenta()+";";
		
		return pers.ExecuteUpdate(query)>0;
		
	}
	
	public boolean remover (Ementa ementa){
		
		if(ementa == null || ementa.getNome().isEmpty()) return false;
		
		if(pesqDisc.pesquisar(ementa.getDisciplina().getIdDisicplina()) == null) return false;
		
		if(pesqEmenta.pesquisar(ementa.getIdEmenta())== null) return false;
		
		this.query = "delete from ementa where idementa = "+ementa.getIdEmenta()+";";
		
		return pers.ExecuteUpdate(query)>0;
		
	}


}
