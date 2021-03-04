package br.com.collabora.dao.alunos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Aluno;
import modelObjects.Turma;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class AlunoDaoImpl implements AlunoDao {

	final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Aluno aluno) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Aluno aluno) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from aluno where idaluno = %s", id)) > 0;
	}

	@Override
	public Optional<Aluno> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers.ExecuteQuery(String.format("select * from aluno where idaluno = %s", id))).stream()
						.findFirst();
	}

	public Optional<Aluno> obter(Aluno aluno) {

		if (aluno == null || aluno.getNome().isEmpty() || aluno.getSenha().isEmpty()) {
			return Optional.empty();
		}

		return this
				.transformar(pers.ExecuteQuery(String.format("select * from aluno where nome = '%s' and senha = '%s'",
						aluno.getNome(), aluno.getSenha())))
				.stream().findFirst();
	}

	@Override
	public List<Aluno> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from aluno"));
	}

	@Override
	public List<Aluno> listarAlunosDeTurma(Turma turma) {
		if (Optional.ofNullable(turma).isPresent() || turma.getId().trim().isEmpty()) {
			return new ArrayList<>();
		}
		return this.transformar(pers.ExecuteQuery(String.format(
				"select * from aluno a join alunoturma at on a.idaluno = at.idaluno where at.idturma = %d",
				turma.getId())));

	}

	private List<Aluno> transformar(ResultSet result) {
		if (!Optional.ofNullable(result).isPresent()) {
			return new ArrayList<>();
		}

		List<Aluno> alunos = new ArrayList<>();
		try {
			while (result.next()) {
				final Aluno aluno = new Aluno();

				aluno.setNome(result.getString("nome"));
				aluno.setSenha(result.getString("senha"));
				aluno.setRegistro(result.getString("registroacademico"));
				aluno.setId(result.getInt("idaluno"));

				alunos.add(aluno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return alunos;
	}

}
