package br.com.collabora.dao.atividades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.collabora.core.Logger;
import br.com.collabora.dao.execucoes.colaboracoes.ColaboracaoDao;
import br.com.collabora.dao.execucoes.questoes.ExecucaoQuestaoDao;
import br.com.collabora.dao.questoes.QuestaoDao;
import br.com.collabora.modelo.dtos.atividades.AtividadeDTO;
import br.com.collabora.modelo.dtos.atividades.ColaboracoesAtividadeDTO;
import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Atividade;
import modelObjects.Grupo;
import modelObjects.Pontuacao;
import modelObjects.Questao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Stateless
public class AtividadeDaoImpl implements AtividadeDao {

	private final Persistir pers = new Persistir();

	private @Inject QuestaoDao questaoDao;

	private @Inject ExecucaoQuestaoDao execucaoQuestaoDao;

	private @Inject ColaboracaoDao colaboracaoDao;

	@Override
	public boolean inserir(Atividade atividade) {
		throw new NotImplementedException();
	}

	@Override
	public boolean modificar(Atividade atividade) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		return pers.ExecuteUpdate(String.format("delete from atividade where idatividade = %s", id)) > 0;
	}

	@Override
	public Optional<Questao> obterProximaQuestaoParaExecucao(Grupo grupo, Atividade atividade) {
		final List<String> idsQuestoes = execucaoQuestaoDao.listarIdsDeQuestoesFinalizadasDoGrupoEAtividade(grupo,
				atividade);

		final List<String> idsQuestoesSemExecucao = execucaoQuestaoDao
				.listarIdsDeQuestoesSemExecucaoDoGrupoEAtividade(grupo, atividade);

		final Optional<Questao> questaoEmAberto = questaoDao.obterPrimeiraQuestaoExceto(atividade, Stream
				.of(idsQuestoes, idsQuestoesSemExecucao).flatMap(Collection::stream).collect(Collectors.toList()));

		if (questaoEmAberto.isPresent()) {
			return questaoEmAberto;
		}

		return questaoDao.obterPrimeiraQuestaoExceto(atividade, idsQuestoes);
	}

	@Override
	public Optional<Atividade> obter(String id) {
		return (id == null || id.trim().isEmpty()) ? Optional.empty()
				: transformar(pers
						.ExecuteQuery(String.format("select ati.* from atividade ati where ati.idatividade = %s", id)))
								.stream().findFirst();
	}

	public List<AtividadeDTO> listarDTOsAtividadePendentesDoGrupo(Grupo grupo) {
		final List<AtividadeDTO> dtos = new ArrayList<>();

		final List<Atividade> atividadesPendentes = this.listarAtividadesPendentesDoGrupo(grupo).stream()
				.sorted((o1, o2) -> o2.getFim().compareTo(o1.getFim())).collect(Collectors.toList());

		for (Atividade ati : atividadesPendentes) {
			final AtividadeDTO dto = new AtividadeDTO(ati.getId(), ati.getNome(), ati.getFim(),
					questaoDao.getNumQuestoesAtividade(ati));

			this.listarConteudos(ati).forEach(dto::adicionarConteudo);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<Atividade> listarAtividadesPendentesDoGrupo(Grupo grupo) {

		final List<String> idsAtividades = execucaoQuestaoDao.listarIdsDeAtividadesFinalizadasDoGrupo(grupo);

		final StringBuilder sql = new StringBuilder();
		sql.append("select ati.* from atividade ati ");
		sql.append("join atividadeturma atiTur on atiTur.idatividade = ati.idatividade ");
		sql.append("join grupo g on g.idturma = atiTur.idturma ");
		sql.append("where g.idgrupo = %s and (now() between ati.datainicio and ati.datafim) ");

		if (idsAtividades.isEmpty()) {
			return transformar(pers.ExecuteQuery(String.format(sql.toString(), grupo.getId())));
		}

		sql.append("and ati.idatividade not in (%s)");

		final StringBuilder inStatementBuilder = new StringBuilder();
		idsAtividades.forEach(id -> inStatementBuilder.append(id).append(","));

		final String inStatement = inStatementBuilder.toString();

		return transformar(pers.ExecuteQuery(
				String.format(sql.toString(), grupo.getId(), inStatement.substring(0, inStatement.length() - 1))));
	}

	public List<AtividadeDTO> listarDTOsAtividadesFinalizadasDoGrupo(Grupo grupo) {
		final List<AtividadeDTO> dtos = new ArrayList<>();

		final List<Atividade> atividadesFinalizadas = this.listarAtividadesFinalizadasDoGrupo(grupo).stream()
				.sorted((o1, o2) -> o2.getFim().compareTo(o1.getFim())).collect(Collectors.toList());

		final List<ColaboracoesAtividadeDTO> dtosColaboracoes = colaboracaoDao.listarDTOsColaboracoesAtividades(grupo,
				atividadesFinalizadas);

		for (Atividade ati : atividadesFinalizadas) {
			final List<Pontuacao> pontuacoes = execucaoQuestaoDao.listarPontuacoesDeAtividade(grupo, ati);

			final AtividadeDTO dto = new AtividadeDTO(ati.getId(), ati.getNome(), ati.getFim(),
					questaoDao.getNumQuestoesAtividade(ati), pontuacoes, dtosColaboracoes.stream()
							.filter(c -> c.getIdAtividade().equals(ati.getIdAtividade())).findAny().get());

			this.listarConteudos(ati).forEach(dto::adicionarConteudo);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<Atividade> listarAtividadesFinalizadasDoGrupo(Grupo grupo) {
		final List<String> idsAtividades = execucaoQuestaoDao.listarIdsDeAtividadesFinalizadasDoGrupo(grupo);

		if (idsAtividades.isEmpty()) {
			return new ArrayList<>();
		}

		final StringBuilder sql = new StringBuilder();
		sql.append("select ati.* from atividade ati ");
		sql.append("join atividadeturma atiTur on atiTur.idatividade = ati.idatividade ");
		sql.append("join grupo g on g.idturma = atiTur.idturma ");
		sql.append("where g.idgrupo = %s and ati.idatividade in (%s)");

		final StringBuilder inStatementBuilder = new StringBuilder();
		idsAtividades.forEach(id -> inStatementBuilder.append(id).append(","));

		final String inStatement = inStatementBuilder.toString();

		return transformar(pers.ExecuteQuery(String.format(sql.toString(), grupo.getId(),
				inStatement.substring(0, inStatementBuilder.length() - 1))));
	}

	@Override
	public List<Atividade> listarTodos() {
		return transformar(pers.ExecuteQuery("select ati.* from atividade ati"));
	}

	private List<Atividade> transformar(ResultSet result) {
		if (!Optional.ofNullable(result).isPresent()) {
			return new ArrayList<>();
		}

		List<Atividade> atividades = new ArrayList<>();
		try {
			while (result.next()) {
				final Atividade a = new Atividade();

				a.setFim(result.getTimestamp("datafim"));
				a.setIdAtividade(result.getInt("idatividade"));
				a.setInicio(result.getTimestamp("datainicio"));
				a.setNome(result.getString("nome"));

				atividades.add(a);
			}
		} catch (SQLException e) {
			Logger.getInstance().logError("Falha ao transformar Atividade", e);
			throw new RuntimeException(e);
		}
		return atividades;
	}

	private List<String> listarConteudos(Atividade ati) {
		final StringBuilder query = new StringBuilder();

		query.append("select distinct(c.nome) as conteudo_atividade ");
		query.append("from questaoatividade qati ");
		query.append("join questao q on q.idquestao = qati.idquestao ");
		query.append("join conteudoquestao cq on cq.idquestao = q.idquestao ");
		query.append("join conteudo c on c.idconteudo = cq.idconteudo ");
		query.append("where qati.idatividade =" + ati.getId());

		final ResultSet rs = pers.ExecuteQuery(query.toString());

		final List<String> conteudos = new ArrayList<>();
		try {
			while (rs.next()) {
				conteudos.add(rs.getString("conteudo_atividade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conteudos;
	}

}
