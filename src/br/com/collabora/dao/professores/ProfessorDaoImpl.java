package br.com.collabora.dao.professores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Professor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class ProfessorDaoImpl implements ProfessorDao {

	final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Professor professor) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Professor professor) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from professor where idprofessor = %s", id)) > 0;
	}

	@Override
	public Optional<Professor> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers.ExecuteQuery(String.format("select * from professor where idprofessor = %s", id)))
						.stream().findFirst();
	}

	@Override
	public List<Professor> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from professor"));
	}

	private List<Professor> transformar(ResultSet result) {
		if (!Optional.ofNullable(result).isPresent()) {
			return new ArrayList<>();
		}

		final List<Professor> professores = new ArrayList<>();
		try {
			while (result.next()) {
				final Professor professor = new Professor();

				professor.setNome(result.getString("nome"));
				professor.setSenha(result.getString("senha"));
				professor.setRegistro(result.getString("registroprofessor"));
				professor.setId(result.getInt("idprofessor"));

				professores.add(professor);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return professores;
	}

}
