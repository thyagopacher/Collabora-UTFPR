package businessService.CRUDAprendizagem.pesquisarGrupo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.CRUDAprendizagem.pesquisarAtividade.IPesquisarAtividade;
import businessService.CRUDAprendizagem.pesquisarTurma.IPesquisarTurma;
import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarAluno.IPesquisarAluno;
import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.Grupo;
import modelObjects.Turma;

public class PesquisarGrupo
		implements IPersistencia, IPesqGrupo, IPesquisarAluno, IPesquisarTurma, IPesquisarAtividade {

	private String query;
	private ResultSet result;

	@Override
	public List<Grupo> PesquisarByTurmaEAtividade(Turma turma) {

		if (turma.equals(null) || turma.getId().equals(null))
			return null;

		this.query = "SELECT * FROM grupo WHERE idturma  =" + turma.getId() + ";";

		this.result = pers.ExecuteQuery(query);

		return this.transformar();
	}

	@Override
	public List<Grupo> PesquisarAtividade(Atividade atividade) {

		if (atividade.equals(null) || atividade.getIdAtividade() < 1)
			return null;

		this.query = "SELECT * FROM grupo WHERE idatividade = " + atividade.getIdAtividade() + ";";

		this.result = pers.ExecuteQuery(query);

		return this.transformar();
	}

	private List<Grupo> transformar() {

		if (this.result.equals(null))
			return null;

		Aluno aluno;
		Turma turma;
		List<Grupo> listaGrupo = new ArrayList<>();
		List<Aluno> listaAluno = new ArrayList<>();
		Grupo aux;

		try {
			while (result.next()) {

				listaAluno.clear();

				aux = new Grupo();
				aux.setIdGrupo(result.getInt("idgrupo"));

				String query = "Select * from alunoGrupo where idgrupo = " + aux.getIdGrupo() + ";";
				aux.setAlunos(setarAlunos(pers.ExecuteQuery(query)));

				turma = pesqTurma.pesquisar(result.getInt("idturma"));
				aux.setTurma(turma);

				// aux.setAtividade(pesqAtividade.pesquisar(result.getInt("idatividade")));

				listaGrupo.add(aux);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return listaGrupo;

	}

	private List<Aluno> setarAlunos(ResultSet query) {

		if (query.equals(null))
			return null;

		Aluno aluno;
		List<Aluno> alunos = new ArrayList<>();

		try {
			while (query.next()) {

				aluno = pesqAluno.pesquisar(query.getInt("idaluno"));
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return alunos;

	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public ResultSet getResult() {
		return result;
	}

	public void setResult(ResultSet result) {
		this.result = result;
	}

}
