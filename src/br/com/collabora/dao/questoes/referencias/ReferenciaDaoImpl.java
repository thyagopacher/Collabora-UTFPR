package br.com.collabora.dao.questoes.referencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Atividade;
import modelObjects.Questao;
import modelObjects.Referencia;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class ReferenciaDaoImpl implements ReferenciaDao {

	private final Persistir pers = new Persistir();

	@Override
	public boolean inserir(Referencia referencia) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Referencia referencia) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from atividade where idatividade = %s", id)) > 0;
	}

	@Override
	public Optional<Referencia> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers.ExecuteQuery(String.format("select * from referencia where idreferencia = %s", id)))
						.stream().findFirst();
	}

	@Override
	public List<Referencia> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from referencia"));
	}

	private List<Referencia> transformar(ResultSet rs) {
		if (!Optional.ofNullable(rs).isPresent()) {
			return new ArrayList<>();
		}

		List<Referencia> referencias = new ArrayList<>();
		try {
			while (rs.next()) {
				final Referencia referencia = new Referencia();

				referencia.setAutor(rs.getString("autor"));
				referencia.setEditora(rs.getString("editora"));
				referencia.setIdReferencia(rs.getInt("idreferencia"));
				referencia.setTitulo(rs.getString("titulo"));

				referencias.add(referencia);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return referencias;
	}

}
