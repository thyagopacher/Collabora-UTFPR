package businessService.CRUDAprendizagem.pesquisarAlunosPorTurma;

import java.sql.ResultSet;
import java.util.List;

import modelObjects.Aluno;
import modelObjects.Turma;

public interface IpesqAlunosTurma {

	public List<Aluno> pequisarPorTurma(Turma turma);

	public List<Aluno> transformar(ResultSet rs);

}
