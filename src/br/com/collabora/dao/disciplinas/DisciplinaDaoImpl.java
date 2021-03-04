package br.com.collabora.dao.disciplinas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Disciplina;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class DisciplinaDaoImpl implements DisciplinaDao {

	final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Disciplina disciplina) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Disciplina disciplina) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from disciplina where iddisciplina = %s", id)) > 0;
	}

	@Override
	public Optional<Disciplina> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers.ExecuteQuery(String.format("select * from disciplina where iddisciplina = %s", id)))
						.stream().findFirst();
	}

	@Override
	public List<Disciplina> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from disciplina"));
	}

	private List<Disciplina> transformar(ResultSet result) {
		if (!Optional.ofNullable(result).isPresent()) {
			return new ArrayList<>();
		}

		List<Disciplina> disciplinas = new ArrayList<>();
		try {
			while (result.next()) {

				final Disciplina disciplina = new Disciplina();

				disciplina.setCodigo(result.getString("codigo"));
				disciplina.setNome(result.getString("nome"));
				disciplina.setIdDisicplina(result.getInt("iddisciplina"));

				disciplinas.add(disciplina);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return disciplinas;
	}

}
