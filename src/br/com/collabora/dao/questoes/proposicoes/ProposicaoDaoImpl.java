package br.com.collabora.dao.questoes.proposicoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import br.com.collabora.dao.DaoGenericoImpl;
import modelObjects.Proposicao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class ProposicaoDaoImpl extends DaoGenericoImpl<Proposicao> implements ProposicaoDao {

	@Override
	public boolean inserir(Proposicao obj) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Proposicao obj) {
		throw new NotImplementedException();
	}

	@Override
	public List<Proposicao> listarPorIds(List<String> idsProposicoes) {
		if (idsProposicoes == null || idsProposicoes.isEmpty()) {
			return new ArrayList<>();
		}

		try {
			final StringBuilder inStatementBuilder = new StringBuilder();
			idsProposicoes.stream().map(Integer::parseInt).forEach(id -> inStatementBuilder.append(id).append(","));

			final String inStatement = inStatementBuilder.toString().substring(0,
					inStatementBuilder.toString().length() - 1);

			return transformarConjunto(pers.ExecuteQuery(
					String.format("select prop.* from proposicao prop where prop.idproposicao in (%s)", inStatement)))
							.stream().collect(Collectors.toList());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Id de proposição mudou de tipo? Verificar...");
		}
	}

	@Override
	protected Proposicao transformar(ResultSet rs) throws SQLException {
		final Proposicao p = new Proposicao();
		p.setCorreta(rs.getBoolean("correta"));
		p.setEnunciado(rs.getString("enunciado"));
		p.setIdProposicao(rs.getInt("idproposicao"));
		p.setImagem(rs.getString("imagem"));
		p.setNumero(rs.getString("numero"));
		return p;
	}

	@Override
	protected String getNomeTabela() {
		return "proposicao";
	}

	@Override
	protected String getNomeCampoId() {
		return "idproposicao";
	}

}
