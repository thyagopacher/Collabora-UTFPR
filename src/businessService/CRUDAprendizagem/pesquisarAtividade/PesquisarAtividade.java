package businessService.CRUDAprendizagem.pesquisarAtividade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import businessService.CRUDAprendizagem.pesquisarQuestao.IPesquisarQuestao;
import businessService.dbConnection.persistencia.IPersistencia;
import modelObjects.Aluno;
import modelObjects.Atividade;

public class PesquisarAtividade implements IpesqAtividade, IPersistencia, IPesquisarQuestao {

	private String query;
	private Atividade aux;

	public Atividade pesquisar(int id) {

		if (id < 1)
			return null;

		query = "select * from atividade where idatividade = " + id + ";";

		aux = this.transformar(pers.ExecuteQuery(query)).stream().findFirst().get();

		query = "select idquestao from questaoatividade where idatividade = " + id + ";";

		List<Integer> ids = this.transformarID(pers.ExecuteQuery(query));
		aux.setQuestoes(new ArrayList<>());

		for (Integer valor : ids) {

			aux.getQuestoes().add(pesqQuestao.pesquisar(valor));

		}

		return aux;

	}

	public List<Atividade> pesquisarAll() {

		this.query = "select * from atividade;";

		ResultSet rs = pers.ExecuteQuery(query);
		List<Atividade> lista = new ArrayList<>();
		Atividade atividade;

		try {
			while (rs.next()) {

				atividade = new Atividade();

				atividade.setFim(rs.getDate("datafim"));
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setInicio(rs.getDate("datainicio"));
				atividade.setNome(rs.getString("nome"));

				lista.add(atividade);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return lista;

	}

	public Atividade pesquisar(Atividade atividade) {

		if (atividade == null || atividade.getNome().isEmpty())
			return null;

		this.query = "select * from atividade where nome = " + atividade.getNome() + ";";

		aux = this.transformar(pers.ExecuteQuery(query)).stream().findFirst().get();

		query = "select idquestao from questaoatividade where idatividade = " + aux.getIdAtividade() + ";";

		List<Integer> ids = this.transformarID(pers.ExecuteQuery(query));
		aux.setQuestoes(new ArrayList<>());

		for (Integer valor : ids) {

			aux.getQuestoes().add(pesqQuestao.pesquisar(valor));

		}

		return aux;
	}

	@Override
	public List<Atividade> pesquisarAbertasComAluno(Aluno aluno) {
		return Arrays.asList(new Atividade("Atividade Luan 2", parse(LocalDate.of(2016, 8, 15)), 2),
				new Atividade("Atividade Luan 3", parse(LocalDate.of(2016, 8, 28)), 3));
	}

	@Override
	public List<Atividade> pesquisarHistoricoDeAluno(Aluno aluno) {
		return Arrays.asList(
				new Atividade("Atividade Luan 1", parse(LocalDate.of(2016, 7, 1)), parse(LocalDate.of(2016, 8, 1)), 1));

	}

	private Date parse(LocalDate ld) {
		return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public List<Atividade> transformar(ResultSet rs) {

		final List<Atividade> atividades = new ArrayList<>();

		if (!Optional.ofNullable(rs).isPresent()) {
			return atividades;
		}

		try {
			while (rs.next()) {

				final Atividade a = new Atividade();

				a.setFim(rs.getDate("datafim"));
				a.setIdAtividade(rs.getInt("idatividade"));
				a.setInicio(rs.getDate("datainicio"));
				a.setNome(rs.getString("nome"));

				atividades.add(a);
			}

			return atividades;
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private List<Integer> transformarID(ResultSet rs) {

		List<Integer> ids = new ArrayList<>();
		if (rs == null)
			return null;

		try {
			while (rs.next()) {

				ids.add(rs.getInt(1));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return ids;
	}
}
