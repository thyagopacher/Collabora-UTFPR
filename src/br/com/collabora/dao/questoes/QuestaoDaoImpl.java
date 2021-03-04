package br.com.collabora.dao.questoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.collabora.dao.questoes.referencias.ReferenciaDao;
import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Atividade;
import modelObjects.Proposicao;
import modelObjects.Questao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class QuestaoDaoImpl implements QuestaoDao {

	private final Persistir pers = new Persistir();

	private @Inject ReferenciaDao referenciaDao;

	@Override
	public boolean inserir(Questao questao) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Questao questao) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from questao where idquestao = %s", id)) > 0;
	}

	@Override
	public Integer getNumQuestoesAtividade(Atividade ati) {
		final ResultSet rs = pers.ExecuteQuery(
				"select count(*) as num_questoes from questaoatividade where idatividade = " + ati.getId());

		try {
			if (rs.next()) {
				return rs.getInt("num_questoes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return 0;
	}

	@Override
	public Optional<Questao> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers.ExecuteQuery(String.format("select * from questao where idquestao = %s", id)))
						.stream().findFirst();
	}

	@Override
	public Optional<Questao> obterPrimeiraQuestaoExceto(Atividade atividade, List<String> idsQuestoes) {
		if (atividade == null || idsQuestoes == null) {
			return Optional.empty();
		}

		final StringBuilder query = new StringBuilder();

		query.append("select q.* from questaoatividade qat ");
		query.append("join questao q on q.idquestao = qat.idquestao ");
		query.append("where qat.idatividade = ").append(atividade.getId());

		if (!idsQuestoes.isEmpty()) {
			final StringBuilder inStatementBuilder = new StringBuilder();
			idsQuestoes.forEach(id -> inStatementBuilder.append(id).append(","));

			final String inStatement = inStatementBuilder.toString();

			query.append(String.format(" and qat.idquestao not in (%s)",
					inStatement.substring(0, inStatement.length() - 1)));
		}

		query.append(" order by qat.idquestao limit 1");

		return transformar(pers.ExecuteQuery(query.toString())).stream().findFirst();
	}

	@Override
	public List<Questao> listarTodos() {
		return transformar(pers.ExecuteQuery("select * from questao"));
	}

	private List<Questao> transformar(ResultSet rs) {
		if (!Optional.ofNullable(rs).isPresent()) {
			return new ArrayList<>();
		}

		List<Questao> questoes = new ArrayList<>();
		try {
			while (rs.next()) {
				final Questao q = new Questao();

				q.setIdQuestao(rs.getInt("idquestao"));
				q.setDificuldade(rs.getString("dificuldade"));
				q.setImagem(rs.getString("imagem"));
				q.setEnunciado(rs.getString("enunciado"));
				q.setPeso(rs.getDouble("peso"));
				q.setReferencia(
						referenciaDao.obter(rs.getInt("idreferencia") + "").orElseThrow(IllegalArgumentException::new));
				q.setProposicoes(new ArrayList<>());

				listarProposicoes(q).forEach(q.getProposicoes()::add);

				questoes.add(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return questoes;
	}

	private List<Proposicao> listarProposicoes(Questao q) {
		final ResultSet rs = pers.ExecuteQuery("select * from proposicao where idquestao = " + q.getId());

		List<Proposicao> proposicoes = new ArrayList<>();
		try {
			while (rs.next()) {
				final Proposicao p = new Proposicao();

				p.setIdProposicao(rs.getInt("idproposicao"));
				p.setCorreta(rs.getBoolean("correta"));
				p.setNumero(rs.getString("numero"));
				p.setImagem(rs.getString("imagem"));
				p.setEnunciado(rs.getString("enunciado"));

				proposicoes.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return proposicoes;
	}

}
