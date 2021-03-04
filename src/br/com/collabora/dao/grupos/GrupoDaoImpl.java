package br.com.collabora.dao.grupos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.collabora.dao.alunos.AlunoDao;
import br.com.collabora.dao.atividades.AtividadeDao;
import br.com.collabora.dao.turmas.TurmaDao;
import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Aluno;
import modelObjects.Grupo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class GrupoDaoImpl implements GrupoDao {

	private @Inject AlunoDao alunoDao;

	private @Inject TurmaDao turmaDao;

	// private @Inject AtividadeDao atividadeDao;

	private final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Grupo grupo) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Grupo grupo) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate("delete from grupo where idgrupo = " + id) > 0;
	}

	@Override
	public Optional<Grupo> obter(String id) {
		return transformar(pers.ExecuteQuery("select * from grupo where idgrupo = " + id)).stream().findFirst();
	}

	@Override
	public List<Grupo> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from grupo"));
	}

	@Override
	public Optional<Grupo> obterGrupoAluno(Aluno aluno) {
		if (!Optional.ofNullable(aluno).isPresent()) {
			return Optional.empty();
		}

		final String query = String.format(
				"select g.* from aluno al join alunogrupo ag on ag.idaluno = al.idaluno join grupo g on g.idgrupo = ag.idgrupo where al.idaluno = %s",
				aluno.getId());

		final ResultSet result = pers.ExecuteQuery(query);

		return transformar(result).stream().findFirst();
	}

	private List<Aluno> obterIntegrantes(Grupo grupo) {
		if (!Optional.ofNullable(grupo).isPresent()) {
			return Collections.emptyList();
		}

		final String query = String.format(
				"select al.idaluno  from aluno al join alunogrupo ag on ag.idaluno = al.idaluno join grupo g on g.idgrupo = ag.idgrupo where g.idgrupo = %s",
				grupo.getId());

		final ResultSet result = pers.ExecuteQuery(query);

		final List<Aluno> integrantes = new ArrayList<>();
		try {
			while (result.next()) {
				alunoDao.obter(String.valueOf(result.getInt("idaluno"))).ifPresent(integrantes::add);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return integrantes;
	}

	private List<Grupo> transformar(ResultSet result) {

		if (result == null) {
			return null;
		}

		List<Grupo> listaGrupo = new ArrayList<>();
		try {
			while (result.next()) {
				final Grupo aux = new Grupo();
				aux.setIdGrupo(result.getInt("idgrupo"));

				aux.setAlunos(this.obterIntegrantes(aux));

				aux.setTurma(turmaDao.obter(String.valueOf(result.getInt("idturma"))).orElse(null));

				// aux.setAtividade(atividadeDao.obter(String.valueOf(result.getInt("idatividade"))).orElse(null));

				listaGrupo.add(aux);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return listaGrupo;

	}

}
