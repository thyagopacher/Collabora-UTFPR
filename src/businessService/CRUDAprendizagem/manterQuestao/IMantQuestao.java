package businessService.CRUDAprendizagem.manterQuestao;

import modelObjects.Questao;

public interface IMantQuestao {
	
	public boolean insert(Questao quest);
	public boolean update(Questao quest);
	public boolean delete(Questao quest);

}
