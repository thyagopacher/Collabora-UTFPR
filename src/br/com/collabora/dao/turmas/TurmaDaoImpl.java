package br.com.collabora.dao.turmas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.collabora.dao.alunos.AlunoDao;
import br.com.collabora.dao.disciplinas.DisciplinaDao;
import br.com.collabora.dao.professores.ProfessorDao;
import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Turma;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class TurmaDaoImpl implements TurmaDao {

	private @Inject AlunoDao alunoDao;

	// private @Inject DisciplinaDao disciplinaDao;

	private @Inject ProfessorDao professorDao;

	final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Turma turma) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Turma turma) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from turma where idturma = %s", id)) > 0;
	}

	@Override
	public Optional<Turma> obter(String id) {
		return transformar(pers.ExecuteQuery(String.format("select * from turma where idturma = %s", id))).stream()
				.findFirst();
	}

	@Override
	public List<Turma> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from turma"));
	}

	private List<Turma> transformar(ResultSet result) {
		if (!Optional.ofNullable(result).isPresent()) {
			return new ArrayList<>();
		}

		final List<Turma> turmas = new ArrayList<>();
		try {
			while (result.next()) {

				Turma turma = new Turma();

				turma.setCodigo(result.getString("codigo"));
				turma.setId(result.getInt("idturma"));

				// turma.setDisciplina(disciplinaDao.obter(String.valueOf(result.getInt("iddisciplina"))).orElse(null));
				turma.setProfessor(professorDao.obter(String.valueOf(result.getInt("idprofessor"))).orElse(null));
				turma.setAluno(alunoDao.listarAlunosDeTurma(turma));

				turmas.add(turma);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return turmas;
	}

}
