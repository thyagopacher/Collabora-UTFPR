package businessService.CRUDAprendizagem.manterReferencia;

import java.util.Calendar;

import modelObjects.Referencia;

public class teste implements IManterReferencia{
	
	public static void main(String[] args) {
		
		Referencia r = new Referencia();
		
		r.setAutor("teste");
		r.setEditora("TESTE");
		r.setTitulo("testetetetet");
		r.setData(Calendar.getInstance());
		r.setIdReferencia(5);
		
		System.out.println(mantRef.Delete(r));
		
	}
}
