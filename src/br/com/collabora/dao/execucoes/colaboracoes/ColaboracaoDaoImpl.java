package br.com.collabora.dao.execucoes.colaboracoes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ejb.Stateless;

import org.apache.commons.lang.NotImplementedException;

import br.com.collabora.dao.DaoGenericoImpl;
import br.com.collabora.dao.alunos.AlunoDao;
import br.com.collabora.facades.FacadeEJB;
import br.com.collabora.modelo.dtos.atividades.ColaboracoesAtividadeDTO;
import businessService.dbConnection.conexao.Conexao;
import businessService.dbConnection.persistencia.Persistir;
import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.Colaboracao;
import modelObjects.ColaboracaoComAnexo;
import modelObjects.ColaboracaoTextual;
import modelObjects.Grupo;

@Stateless
public class ColaboracaoDaoImpl extends DaoGenericoImpl<Colaboracao> implements ColaboracaoDao {

	@Override
	public boolean inserir(Colaboracao colab) {
		return ManipuladorColaboracao.obterPorTipo(colab.getClass()).inserir(colab);
	}

	@Override
	public boolean modificar(Colaboracao colab) {
		throw new NotImplementedException();
	}

	@Override
	protected Colaboracao transformar(ResultSet rs) {
		try {
			return ManipuladorColaboracao.obterPorIe(rs.getInt(ManipuladorColaboracao.COLUNA_IE_COLABORACAO))
					.transformarItem(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<byte[]> obterBytesArquivo(String id) {
		final ResultSet rs = pers
				.ExecuteQuery(String.format("select arquivo from colaboracao where id_colaboracao = '%s'", id));

		try {
			if (rs.next()) {
				byte[] imgBytes = rs.getBytes("arquivo");

				try {
					return Optional.ofNullable(imgBytes);
				} finally {
					rs.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<ColaboracoesAtividadeDTO> listarDTOsColaboracoesAtividades(Grupo g, List<Atividade> atividades) {
		if (atividades == null || atividades.isEmpty()) {
			return new ArrayList<>();
		}

		final StringBuilder query = new StringBuilder();
		query.append("select qat.idatividade, ");
		query.append("(select max(execq.fim) from questaoatividade qat2 ");
		query.append("join execucao_questao execq on execq.id_questao = qat2.idquestao and execq.id_grupo = %s ");
		query.append("where qat2.idatividade = qat.idatividade) as ultima_exec, ");
		query.append("(select count(col.*) from questaoatividade qat2 ");
		query.append("join execucao_questao execq on execq.id_questao = qat2.idquestao and execq.id_grupo = %s ");
		query.append(
				"join colaboracao col on col.id_execucao_questao = execq.id_execucao_questao and col.ie_colaboracao = 1 ");
		query.append("where qat2.idatividade = qat.idatividade) as num_colabs_texto, ");
		query.append("(select count(col.*) from questaoatividade qat2 ");
		query.append("join execucao_questao execq on execq.id_questao = qat2.idquestao and execq.id_grupo = %s ");
		query.append(
				"join colaboracao col on col.id_execucao_questao = execq.id_execucao_questao and col.ie_colaboracao = 2 ");
		query.append("where qat2.idatividade = qat.idatividade ) as num_colabs_arquivo ");
		query.append("from questaoatividade qat ");
		query.append("where qat.idatividade in (%s) ");
		query.append("group by qat.idatividade ");

		final StringBuilder inStatementBuilder = new StringBuilder();
		atividades.stream().map(Atividade::getId).forEach(id -> inStatementBuilder.append(id).append(","));

		final String inStatement = inStatementBuilder.toString().substring(0,
				inStatementBuilder.toString().length() - 1);

		final String q = String.format(query.toString(), g.getId(), g.getId(), g.getId(), inStatement);

		final ResultSet rs = pers.ExecuteQuery(q);

		final List<ColaboracoesAtividadeDTO> dtos = new ArrayList<>();

		try {
			while (rs.next()) {
				final ColaboracoesAtividadeDTO dto = new ColaboracoesAtividadeDTO(rs.getInt("idatividade"),
						rs.getTimestamp("ultima_exec"), rs.getInt("num_colabs_texto"), rs.getInt("num_colabs_arquivo"));

				dtos.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return dtos;
	}

	private enum ManipuladorColaboracao {
		TEXTUAL(ColaboracaoTextual.class, 1) {
			@Override
			protected boolean inserir(Colaboracao colab) {
				final Persistir pers = new Persistir();

				final ColaboracaoTextual colaboracaoTextual = (ColaboracaoTextual) colab;

				final String query = String.format(
						"INSERT INTO COLABORACAO (ID_COLABORACAO, ID_EXECUCAO_QUESTAO, ID_ALUNO, IE_COLABORACAO, MENSAGEM, DATA_HORA_OCORRENCIA) "
								+ "VALUES ('%s', '%s', '%s', %d,'%s', now())",
						colaboracaoTextual.getId(), colaboracaoTextual.getIdExecucaoQuestao(),
						colaboracaoTextual.getResponsavel().getId(), ieColaboracao, colaboracaoTextual.getMensagem());

				return (pers.ExecuteUpdate(query) > 0);
			}

			@Override
			protected Colaboracao transformarItem(ResultSet rs) {
				try {
					final LocalDateTime ocorrencia = rs.getDate("COLUNA_DATA_HORA_OCORRENCIA").toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDateTime();

					final Aluno responsavel = FacadeEJB.obterPelaClasseDoBean(AlunoDao.class)
							.obter(rs.getInt(COLUNA_ID_ALUNO) + "").orElseThrow(IllegalArgumentException::new);

					return new ColaboracaoTextual(rs.getString(COLUNA_ID_COLABORACAO),
							rs.getString(COLUNA_ID_EXECUCAO_QUESTAO), responsavel, ocorrencia,
							rs.getString(COLUNA_MENSAGEM));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		},
		COM_ANEXO(ColaboracaoComAnexo.class, 2) {
			@Override
			protected boolean inserir(Colaboracao colab) {
				final ColaboracaoComAnexo colaboracaoComAnexo = (ColaboracaoComAnexo) colab;
				try {
					Conexao c = new Conexao();
					FileInputStream fis = null;
					PreparedStatement ps = null;
					Connection conexao = null;
					try {
						conexao = c.conectar();
						int index = 0;
						fis = new FileInputStream(colaboracaoComAnexo.getArquivo());
						ps = conexao.prepareStatement(
								"INSERT INTO COLABORACAO (ID_COLABORACAO, ID_EXECUCAO_QUESTAO, ID_ALUNO, IE_COLABORACAO, NOME_ARQUIVO, TIPO_MIDIA_ARQUIVO, TAMANHO_ARQUIVO,"
										+ " ARQUIVO, DATA_HORA_OCORRENCIA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())");
						ps.setString(++index, colaboracaoComAnexo.getId());
						ps.setString(++index, colaboracaoComAnexo.getIdExecucaoQuestao());
						ps.setInt(++index, Integer.parseInt(colaboracaoComAnexo.getResponsavel().getId()));
						ps.setInt(++index, ieColaboracao);
						ps.setString(++index, colaboracaoComAnexo.getNome());
						ps.setString(++index, colaboracaoComAnexo.getTipoMidia());
						ps.setLong(++index, (int) colaboracaoComAnexo.getArquivo().length());
						ps.setBinaryStream(++index, fis, (int) colaboracaoComAnexo.getArquivo().length());
						ps.executeUpdate();

						colaboracaoComAnexo.removerReferenciaArquivo();

						return true;
					} catch (FileNotFoundException | SQLException e) {
						e.printStackTrace();
					} finally {
						if (fis != null) {
							fis.close();
						}

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
			protected Colaboracao transformarItem(ResultSet rs) {
				try {
					final LocalDateTime ocorrencia = rs.getDate("COLUNA_DATA_HORA_OCORRENCIA").toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDateTime();

					return new ColaboracaoComAnexo(rs.getString(COLUNA_ID_COLABORACAO),
							rs.getString(COLUNA_ID_EXECUCAO_QUESTAO), null, ocorrencia, null,
							rs.getString(COLUNA_TIPO_MIDIA_ARQUIVO), (long) rs.getInt(COLUNA_TAMANHO_ARQUIVO),
							rs.getString(COLUNA_NOME_ARQUIVO));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};

		protected final String COLUNA_ID_COLABORACAO = "ID_COLABORACAO";
		protected final String COLUNA_ID_EXECUCAO_QUESTAO = "ID_EXECUCAO_QUESTAO";
		protected final String COLUNA_ID_ALUNO = "ID_ALUNO";
		public static final String COLUNA_IE_COLABORACAO = "IE_COLABORACAO";
		protected final String COLUNA_MENSAGEM = "MENSAGEM";
		protected final String COLUNA_NOME_ARQUIVO = "NOME_ARQUIVO";
		protected final String COLUNA_TIPO_MIDIA_ARQUIVO = "TIPO_MIDIA_ARQUIVO";
		protected final String COLUNA_TAMANHO_ARQUIVO = "TAMANHO_ARQUIVO";
		protected final String COLUNA_ARQUIVO = "ARQUIVO";
		protected final String COLUNA_DATA_HORA_OCORRENCIA = "DATA_HORA_OCORRENCIA";

		private final Class<? extends Colaboracao> tipo;
		protected final int ieColaboracao;

		ManipuladorColaboracao(Class<? extends Colaboracao> tipo, int ieColaboracao) {
			this.tipo = tipo;
			this.ieColaboracao = ieColaboracao;
		}

		protected boolean mesmoTipo(Class<? extends Colaboracao> tipo) {
			return this.tipo.equals(tipo);
		}

		protected boolean mesmoIe(int ie) {
			return this.ieColaboracao == ie;
		}

		protected abstract boolean inserir(Colaboracao colab);

		protected abstract Colaboracao transformarItem(ResultSet rs);

		private static <T extends Colaboracao> ManipuladorColaboracao obterPorTipo(Class<T> t) {
			return Stream.of(ManipuladorColaboracao.values()).filter(mc -> mc.mesmoTipo(t)).findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

		private static <T extends Colaboracao> ManipuladorColaboracao obterPorIe(int ie) {
			return Stream.of(ManipuladorColaboracao.values()).filter(mc -> mc.mesmoIe(ie)).findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}
	}

	@Override
	protected String getNomeTabela() {
		return Colaboracao.class.getSimpleName().toLowerCase();
	}

	@Override
	protected String getNomeCampoId() {
		return "id_colaboracao";
	}

}
