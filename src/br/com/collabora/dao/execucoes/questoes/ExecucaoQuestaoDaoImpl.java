package br.com.collabora.dao.execucoes.questoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import javax.ejb.Stateless;

import org.apache.commons.lang.NotImplementedException;

import br.com.collabora.dao.DaoGenericoImpl;
import businessService.dbConnection.conexao.Conexao;
import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.ExecucaoQuestao;
import modelObjects.FeedbackAtividade;
import modelObjects.FeedbackColaboracao;
import modelObjects.Grupo;
import modelObjects.Pontuacao;
import modelObjects.Proposicao;
import modelObjects.Questao;
import modelObjects.RespostaQuestao;
import modelObjects.StatusQuestao;

@Stateless
public class ExecucaoQuestaoDaoImpl extends DaoGenericoImpl<ExecucaoQuestao> implements ExecucaoQuestaoDao {

	@Override
	public boolean finalizarExecucao(String id) {

		final String query = String.format("update %s set fim = now() where id_execucao_questao = '%s'",
				this.getNomeTabela(), id);

		return pers.ExecuteUpdate(query) > 0;
	}

	@Override
	public boolean indefinir(String id) {
		final String query = String.format(
				"update %s set ie_status_questao = '%s', fim = now() where id_execucao_questao = '%s'",
				this.getNomeTabela(), StatusQuestao.INDEFINIDA, id);
		
		return pers.ExecuteUpdate(query) > 0;
	}

	private void empatar(ExecucaoQuestao execucaoQuestao) {
		Conexao c = new Conexao();
		Connection con = c.conectar();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);

			for (RespostaQuestao r : execucaoQuestao.getRespostas()) {
				inserirRespostaProposicao(con, ps, execucaoQuestao.getId(), r.getProposicao(),
						Optional.of(r.getAluno()));
			}

			alterarStatusExecucaoQuestaoEFinalizar(con, ps, execucaoQuestao.getId(), StatusQuestao.SEM_CONSENSO);

			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}

				if(con != null){
					con.close();
					con = null;
				}
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
	}

	private void finalizarComConsenso(String id, List<Proposicao> proposicoes) {
		Conexao c = new Conexao();
		Connection con = c.conectar();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);

			for (Proposicao p : proposicoes) {
				inserirRespostaProposicao(con, ps, id, p, Optional.empty());
			}

			alterarStatusExecucaoQuestaoEFinalizar(con, ps, id, StatusQuestao.FINALIZADA);

			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}

				if(con != null){
					con.close();
					con = null;
				}
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
	}

	private void inserirRespostaProposicao(Connection con, PreparedStatement ps, String id, Proposicao proposicao,
			Optional<Aluno> aluno) throws SQLException {
		final String query = "insert into resposta_questao(id_resposta_questao, id_execucao_questao, id_proposicao,id_aluno) values (?, ?, ?, %s)";

		int index = 0;
		ps = con.prepareStatement(String.format(query, aluno.isPresent() ? "?" : "null"));
		ps.setString(++index, UUID.randomUUID().toString());
		ps.setString(++index, id);
		ps.setInt(++index, proposicao.getIdProposicao());
		if (aluno.isPresent()) {
			ps.setInt(++index, Integer.parseInt(aluno.get().getId()));
		}

		ps.executeUpdate();
	}

	private void alterarStatusExecucaoQuestaoEFinalizar(Connection con, PreparedStatement ps, String id,
			StatusQuestao status) throws SQLException {
		final String query = "update %s set ie_status_questao = '%s', fim = now() where id_execucao_questao = ?";

		ps = con.prepareStatement(String.format(query, this.getNomeTabela(), status));
		ps.setString(1, id);

		ps.executeUpdate();
	}

	@Override
	public StatusQuestao atualizarRespostasEMudarStatus(ExecucaoQuestao execucaoQuestao) {
		if (!execucaoQuestao.temRespostaDeMaisDeUmAluno()) {
			System.out.println("Finalizar como automática");

			indefinir(execucaoQuestao.getId());

			return StatusQuestao.INDEFINIDA;
		} else if (!execucaoQuestao.semConsenso()) {
			System.out.println("Finalizar com Consenso");

			finalizarComConsenso(execucaoQuestao.getId(), execucaoQuestao.obterConsenso());

			return StatusQuestao.FINALIZADA;
		} else {
			System.out.println("Finalizar com empatada");

			empatar(execucaoQuestao);

			return StatusQuestao.SEM_CONSENSO;
		}
	}

	@Override
	public List<String> listarIdsDeAtividadesFinalizadasDoGrupo(Grupo grupo) {
		if (grupo == null) {
			return new ArrayList<String>();
		}

		final StringBuilder query = new StringBuilder();
		query.append("select atitur.idatividade from atividadeturma atitur ");
		query.append("join grupo g on g.idturma = atitur.idturma ");
		query.append("where g.idgrupo = %s and ");
		query.append(
				"(select count(qati.idquestao) from questaoatividade qati where qati.idatividade = atitur.idatividade) = ");
		query.append("(select count(distinct(qati.idquestao)) from questaoatividade qati ");
		query.append(
				"join execucao_questao exe on exe.id_questao = qati.idquestao and exe.id_grupo = %s and exe.ie_status_questao <> '%s' ");
		query.append("where qati.idatividade = atitur.idatividade)");

		final ResultSet rs = pers.ExecuteQuery(
				String.format(query.toString(), grupo.getId(), grupo.getId(), StatusQuestao.EM_ABERTO.toString()));

		final List<String> ids = new ArrayList<>();

		try {
			while (rs.next()) {
				ids.add(String.valueOf(rs.getInt("idatividade")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return ids;
	}

	@Override
	public List<String> listarIdsDeQuestoesFinalizadasDoGrupoEAtividade(Grupo grupo, Atividade atividade) {
		if (grupo == null || atividade == null) {
			return new ArrayList<String>();
		}

		final StringBuilder query = new StringBuilder();

		query.append("select distinct(qat.idquestao) from questaoatividade qat ");
		query.append("join execucao_questao eq on eq.id_questao = qat.idquestao ");
		query.append("where eq.id_grupo = %s and qat.idatividade = %s and eq.ie_status_questao <> '%s'");

		final ResultSet rs = pers.ExecuteQuery(
				String.format(query.toString(), grupo.getId(), atividade.getId(), StatusQuestao.EM_ABERTO.toString()));

		final List<String> ids = new ArrayList<>();

		try {
			while (rs.next()) {
				ids.add(String.valueOf(rs.getInt("idquestao")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return ids;
	}

	@Override
	public List<String> listarIdsDeQuestoesSemExecucaoDoGrupoEAtividade(Grupo grupo, Atividade atividade) {
		if (grupo == null || atividade == null) {
			return new ArrayList<>();
		}

		final StringBuilder query = new StringBuilder();
		query.append("select q.idquestao from questaoatividade qat join questao q on q.idquestao = qat.idquestao ");
		query.append("where qat.idatividade = %s and 0 = (select count(*) from execucao_questao exeq ");
		query.append("where exeq.id_grupo = %s and exeq.id_questao = q.idquestao) order by q.idquestao");

		final ResultSet rs = pers.ExecuteQuery(String.format(query.toString(), atividade.getId(), grupo.getId()));

		final List<String> ids = new ArrayList<>();

		try {
			while (rs.next()) {
				ids.add(String.valueOf(rs.getInt("idquestao")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return ids;
	}

	@Override
	public boolean inserir(ExecucaoQuestao execucaoQuestao) {
		try {
			Conexao c = new Conexao();
			PreparedStatement ps = null;
			Connection conexao = null;
			try {
				conexao = c.conectar();
				int index = 0;
				ps = conexao.prepareStatement(
						"Insert into execucao_questao (id_execucao_questao, id_grupo, id_questao, inicio, ie_status_questao) values (?, ?, ?, now(), ?);");
				ps.setString(++index, execucaoQuestao.getId());
				ps.setInt(++index, execucaoQuestao.getExecutor().getIdGrupo());
				ps.setInt(++index, execucaoQuestao.getReferencia().getIdQuestao());
				ps.setString(++index, StatusQuestao.EM_ABERTO.toString());
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}

				if (conexao != null && !conexao.isClosed()) {
					conexao.close();
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean modificar(ExecucaoQuestao obj) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remover(String id) {
		throw new NotImplementedException();
	}

	@Override
	public Optional<ExecucaoQuestao> obter(String id) {
		throw new NotImplementedException();
	}

	@Override
	public List<Duration> buscarPeriodosJaExecutados(Grupo grupo, Questao questao) {

		final ResultSet rs = pers.ExecuteQuery(String.format(
				"select eq.inicio, eq.fim from execucao_questao eq where id_grupo = %s and id_questao = %s",
				String.valueOf(grupo.getId()), String.valueOf(questao.getId())));

		final List<Duration> periodos = new ArrayList<>();
		try {
			while (rs.next()) {
				final LocalDateTime inicio = new java.util.Date(rs.getTimestamp("inicio").getTime()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime fim = new java.util.Date(rs.getTimestamp("fim").getTime()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDateTime();

				periodos.add(Duration.between(inicio, fim));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return periodos;
	}

	@Override
	protected ExecucaoQuestao transformar(ResultSet rs) {
		throw new NotImplementedException();
	}

	@Override
	protected String getNomeTabela() {
		return "execucao_questao";
	}

	@Override
	protected String getNomeCampoId() {
		return "id_execucao_questao";
	}

	public FeedbackAtividade obterFeedbackAtividade(Grupo grupo, Atividade atividade) {
		final Supplier<List<FeedbackColaboracao>> feebacksColaboracao = () -> listarFeedbacksColaboracao(grupo,
				atividade);
		final Supplier<List<Pontuacao>> pontuacoes = () -> listarPontuacoesDeAtividade(grupo, atividade);

		return new FeedbackAtividade(atividade, grupo, feebacksColaboracao.get(), pontuacoes.get());
	}

	public List<FeedbackColaboracao> listarFeedbacksColaboracao(Grupo grupo, Atividade atividade) {
		final StringBuilder sql = new StringBuilder();
		sql.append(
				"select col.id_aluno, al.nome as nome_aluno, count(distinct(col.id_colaboracao)) as num_colaboracoes_aluno ");
		sql.append("from questaoatividade qati ");
		sql.append("join questao q on q.idquestao = qati.idquestao ");
		sql.append("join execucao_questao execq on execq.id_questao = q.idquestao ");
		sql.append("join colaboracao col on col.id_execucao_questao = execq.id_execucao_questao ");
		sql.append("join aluno al on al.idaluno = col.id_aluno ");
		sql.append("where qati.idatividade = %s and execq.id_grupo = %s ");
		sql.append("group by col.id_aluno, al.nome");

		final String query = String.format(sql.toString(), String.valueOf(atividade.getIdAtividade()),
				String.valueOf(grupo.getId()));

		final ResultSet rs = pers.ExecuteQuery(query);

		final List<FeedbackColaboracao> feedbackColaboracoes = new ArrayList<>();
		try {
			while (rs.next()) {
				feedbackColaboracoes.add(new FeedbackColaboracao(rs.getInt("id_aluno"), rs.getString("nome_aluno"),
						rs.getInt("num_colaboracoes_aluno")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return feedbackColaboracoes;
	}

	@Override
	public List<Pontuacao> listarPontuacoesDeAtividade(Grupo grupo, Atividade atividade) {
		final StringBuilder sql = new StringBuilder();

		sql.append("select q.idquestao as id_questao, ");
		sql.append(
				"(select count(prop.idproposicao) from proposicao prop where prop.idquestao = q.idquestao) as num_tot_proposicoes, ");
		sql.append(
				"(select count(prop.idproposicao) from proposicao prop where prop.idquestao = q.idquestao and prop.correta = true) as num_tot_proposicoes_corretas, ");
		sql.append("(select count(prop.idproposicao) from proposicao prop ");
		sql.append("where prop.idquestao = q.idquestao and prop.correta = true ");
		sql.append("and 0 < ( ");
		sql.append("select count(*) ");
		sql.append("from execucao_questao execq ");
		sql.append("join resposta_questao rq on rq.id_execucao_questao = execq.id_execucao_questao ");
		sql.append(
				"where execq.id_grupo = %s and execq.id_questao = q.idquestao and prop.idproposicao = rq.id_proposicao  ) ");
		sql.append(") as num_props_corretas, ");
		sql.append("(select count(prop.idproposicao) from proposicao prop ");
		sql.append("where prop.idquestao = q.idquestao and prop.correta = false ");
		sql.append("and 0 < ( ");
		sql.append("select count(*) ");
		sql.append("from execucao_questao execq ");
		sql.append("join resposta_questao rq on rq.id_execucao_questao = execq.id_execucao_questao ");
		sql.append(
				"where execq.id_grupo = %s and execq.id_questao = q.idquestao and prop.idproposicao = rq.id_proposicao  ) ");
		sql.append(") as num_props_incorretas ");
		sql.append("from questaoatividade qati ");
		sql.append("join questao q on q.idquestao = qati.idquestao ");
		sql.append("where qati.idatividade = %s");

		final String query = String.format(sql.toString(), String.valueOf(grupo.getId()), String.valueOf(grupo.getId()),
				String.valueOf(atividade.getIdAtividade()));

		final ResultSet rs = pers.ExecuteQuery(query);

		final List<Pontuacao> pontuacoes = new ArrayList<>();
		try {
			while (rs.next()) {
				pontuacoes.add(new Pontuacao(String.valueOf(rs.getInt("id_questao")), rs.getInt("num_tot_proposicoes"),
						rs.getInt("num_tot_proposicoes_corretas"), rs.getInt("num_props_corretas"),
						rs.getInt("num_props_incorretas")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return pontuacoes;
	}

}
