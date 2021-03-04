package br.com.collabora.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import businessService.dbConnection.persistencia.Persistir;
import modelObjects.ObjetoModelo;

public abstract class DaoGenericoImpl<T extends ObjetoModelo> implements DaoGenerico<T> {

	protected final Persistir pers = new Persistir();

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from %s where %s = %s", getNomeTabela(), getNomeCampoId(), id)) > 0;
	}

	@Override
	public Optional<T> obter(String id) {
		if (id == null || id.trim().isEmpty()) {
			return Optional.empty();
		}

		return transformarConjunto(
				pers.ExecuteQuery(String.format("select * from %s where %s = %s", getNomeTabela(), getNomeCampoId(),  id))).stream()
						.findFirst();
	}

	@Override
	public List<T> listarTodos() {
		return transformarConjunto(pers.ExecuteQuery(String.format("select * from %s ", getNomeTabela()))).stream()
				.collect(Collectors.toList());
	}

	protected abstract T transformar(ResultSet rs) throws SQLException;

	protected Collection<T> transformarConjunto(ResultSet rs) {
		if (!Optional.ofNullable(rs).isPresent()) {
			return new ArrayList<>();
		}

		final List<T> registros = new ArrayList<>();
		try {
			while (rs.next()) {
				registros.add(transformar(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return registros;
	}

	protected abstract String getNomeTabela();
	
	protected String getNomeCampoId(){
		return "id";
	}

}
