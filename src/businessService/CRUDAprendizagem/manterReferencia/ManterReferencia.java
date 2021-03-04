package businessService.CRUDAprendizagem.manterReferencia;

import businessService.CRUDAprendizagem.pesquisarReferencia.IPesquisarReferencia;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Referencia;

public class ManterReferencia implements ImantRef, IPersistencia, IPesquisarReferencia {
	
	private String query;
	
	@Override
	public boolean Insert(Referencia ref) {
		
		if(this.NaoValido(ref))return false;
		
		if(pesqRef.verificarExistencia(ref)) return false;
		
		this.query = "INSERT INTO referencia (autor, titulo, editora, ano) VALUES('"
				+ref.getAutor().toUpperCase()+"','"+ref.getTitulo().toUpperCase()+"','"
				+ref.getEditora().toUpperCase()+"','"+ref.getData().getTime()+"');";
		
//		System.out.println(query);
		
		return (pers.ExecuteUpdate(query)>0);
	}

	@Override
	public boolean Update(Referencia ref) {
		if(this.NaoValido(ref))return false;
		
		if(pesqRef.pesquisar(ref.getIdReferencia()) == null) return false;
		
		this.query = "UPDATE referencia SET autor= '"
				+ref.getAutor().toUpperCase()+"', "
				+ "titulo = '"+ref.getTitulo().toUpperCase()+"',"
				+ " editora = '"+ref.getEditora().toUpperCase()+"', "
				+ "ano = '"+ref.getData().getTime()+"'"
				+ "WHERE idreferencia = "+ref.getIdReferencia()+";";
		
//		System.out.println(query);
		
		return (pers.ExecuteUpdate(query)>0);
	}

	@Override
	public boolean Delete(Referencia ref) {
		if(ref == null || ref.getIdReferencia() < 1)return false;
		
//		System.out.println(1);
		
		if(!pesqRef.verificarExistencia(ref)) return false;
		
//		System.out.println(2);
		
		this.query = "DELETE FROM referencia WHERE idreferencia = "+ref.getIdReferencia()+";";
		
		return (pers.ExecuteUpdate(query)>0);
	}
	
	private boolean NaoValido(Referencia ref){
		
		return (ref == null || ref.getAutor() == null ||ref.getAutor().isEmpty() ||
				ref.getData()== null|| ref.getEditora() == null || ref.getEditora().isEmpty() ||
				ref.getTitulo() == null || ref.getTitulo().isEmpty());
		
	}

}
