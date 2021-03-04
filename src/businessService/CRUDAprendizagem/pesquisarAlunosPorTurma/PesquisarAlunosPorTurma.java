package businessService.CRUDAprendizagem.pesquisarAlunosPorTurma;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Aluno;
import modelObjects.Turma;

public class PesquisarAlunosPorTurma implements IpesqAlunosTurma, IPersistencia {

	private String query;

	public List<Aluno> pequisarPorTurma(Turma turma) {

		if (turma == null || turma.getId().trim().isEmpty())
			return null;

		this.query = "select * from aluno as a, alunoturma as at where " + "a.idaluno = at.idaluno and at.idturma = "
				+ turma.getId() + ";";

		return this.transformar(pers.ExecuteQuery(query));

	}

	public List<Aluno> transformar(ResultSet rs) {

		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno;

		if (rs == null)
			return null;

		try {

			while (rs.next()) {

				aluno = new Aluno();

				aluno.setId(rs.getInt("idaluno"));
				aluno.setNome(rs.getString("nome"));
				aluno.setRegistro(rs.getString("registroacademico"));
				aluno.setSenha(rs.getString("senha"));

				alunos.add(aluno);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alunos;
	}

}
