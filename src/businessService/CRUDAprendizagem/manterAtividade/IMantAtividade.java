package businessService.CRUDAprendizagem.manterAtividade;

import modelObjects.Atividade;

public interface IMantAtividade {
	
	public boolean insert(Atividade atividade);
	public boolean update(Atividade atividade);
	public boolean delete(Atividade atividade);

}
