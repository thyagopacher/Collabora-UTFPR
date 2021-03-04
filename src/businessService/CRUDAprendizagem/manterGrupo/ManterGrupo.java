package businessService.CRUDAprendizagem.manterGrupo;

import java.sql.ResultSet;
import java.sql.SQLException;

import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Aluno;
import modelObjects.Grupo;

public class ManterGrupo implements IMantGrupo, IPersistencia {
	private String query;

	public boolean inserir(Grupo grupo) {

		if (grupo == null || grupo.getAlunos() == null || grupo.getAlunos().size() > 4 || grupo.getAlunos().size() < 2
				|| grupo.getIdGrupo() == null || grupo.getIdGrupo() < 1 || grupo.getTurma() == null
				|| grupo.getTurma().getId().trim().isEmpty())
			return false;

		this.query = "INSERT into grupo (idturma, aberto) values (" + grupo.getTurma().getId() + ", 'true');";

		if (pers.ExecuteUpdate(query) < 1)
			return false;

		this.query = "SELECT * from grupo where aberto = TRUE;";

		ResultSet rs = pers.ExecuteQuery(query);

		try {
			if (rs.next()) {

				grupo.setIdGrupo(rs.getInt("idgrupo"));
			} else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Aluno aluno : grupo.getAlunos()) {

			this.query = "INSERT into alunoGrupo(aluno, grupo) values(" + aluno.getId() + "," + grupo.getIdGrupo()
					+ ");";

			if (pers.ExecuteUpdate(query) < 1)
				return false;

		}

		this.query = "UPDATE grupo set aberto = FALSE WHERE idgrupo = " + grupo.getIdGrupo() + ");";

		return (pers.ExecuteUpdate(query) > 0);

	}

	public boolean modificar(Grupo grupo) {

		if (grupo == null || grupo.getAlunos().size() > 4 || grupo.getAlunos().size() < 2 || grupo.getIdGrupo() < 1)
			return false;

		if (!this.remover(grupo))
			return false;

		for (Aluno aluno : grupo.getAlunos()) {

			this.query = "UPDATE alunoGrupo SET aluno = " + aluno.getId() + ", grupo = " + grupo.getIdGrupo() + ";";

			if (pers.ExecuteUpdate(query) < 1)
				return false;

		}

		return true;
	}

	public boolean remover(Grupo grupo) {

		if (grupo == null || grupo.getIdGrupo() < 1)
			return false;

		this.query = "delete from alunoGrupo where grupo = " + grupo.getIdGrupo() + " ;";

		return pers.ExecuteUpdate(query) > 0;
	}

}
