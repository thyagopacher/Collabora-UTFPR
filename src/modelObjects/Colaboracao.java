package modelObjects;

import java.time.LocalDateTime;

public interface Colaboracao extends ObjetoModelo {

	String getIdExecucaoQuestao();

	Aluno getResponsavel();

	LocalDateTime getDataHoraOcorrencia();

}
