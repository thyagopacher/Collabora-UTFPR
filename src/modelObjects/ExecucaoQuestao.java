package modelObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.collabora.modelo.colaboracoes.sessoes.ColaboracaoDeSessao;

public class ExecucaoQuestao implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();

	private final Date inicio;

	private Date fim;

	private final Grupo executor;

	private final Questao referencia;

	private StatusQuestao statusQuestao;

	private final Set<RespostaQuestao> respostas = new HashSet<>();

	private final Set<ColaboracaoDeSessao> colaboracoes = new HashSet<>();

	public ExecucaoQuestao(Grupo grupo, Questao referencia) {
		this(grupo, referencia, null);
	}

	public ExecucaoQuestao(Grupo grupo, Questao referencia, Date inicio) {
		this.inicio = inicio;
		this.executor = grupo;
		this.referencia = referencia;
	}

	public Date getInicio() {
		return inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Set<RespostaQuestao> getRespostas() {
		return respostas;
	}

	public Grupo getExecutor() {
		return executor;
	}

	public Questao getReferencia() {
		return referencia;
	}

	public StatusQuestao getStatusQuestao() {
		return statusQuestao;
	}

	public void setStatusQuestao(StatusQuestao statusQuestao) {
		this.statusQuestao = statusQuestao;
	}

	public Set<ColaboracaoDeSessao> getColaboracoes() {
		return colaboracoes;
	}

	public boolean temRespostaDeMaisDeUmAluno() {
		return this.respostas.stream().map(RespostaQuestao::getAluno).distinct().count() > 1;
	}

	public boolean alunoTemResposta(Aluno aluno) {
		return this.respostas.stream().filter(r -> r.dadaPor(aluno)).findAny().isPresent();
	}

	public void incluirResposta(Aluno aluno, List<Proposicao> proposicoes) {
		final List<RespostaQuestao> respostasAntigas = new ArrayList<>();

		this.respostas.stream().filter(r -> r.dadaPor(aluno)).forEach(respostasAntigas::add);

		this.respostas.removeAll(respostasAntigas);

		proposicoes.stream().map(p -> new RespostaQuestao(p, aluno)).forEach(this.respostas::add);
	}

	public void removerRespostas() {
		this.respostas.clear();
	}

	public boolean temRespostas() {
		return !this.respostas.isEmpty();
	}

	public List<Proposicao> obterConsenso() {
		final Aluno base = this.respostas.stream().findAny().get().getAluno();

		return this.respostas.stream().filter(r -> r.dadaPor(base)).map(RespostaQuestao::getProposicao).distinct()
				.collect(Collectors.toList());
	}

	public boolean semConsenso() {
		if (temRespostas()) {

			final List<Aluno> alunos = this.respostas.stream().map(RespostaQuestao::getAluno).distinct()
					.collect(Collectors.toList());

			final Aluno base = obterAlunoComMaiorNumPreposicoes().get();

			final Function<Aluno, List<Proposicao>> proposicoesDoAluno = a -> this.respostas.stream()
					.filter(r -> r.dadaPor(a)).map(RespostaQuestao::getProposicao).collect(Collectors.toList());

			final int baseDeNumPreposicoes = proposicoesDoAluno.apply(base).size();

			boolean mesmoNumDeVotos = true;
			for (Aluno a2 : alunos) {
				if (baseDeNumPreposicoes != proposicoesDoAluno.apply(a2).size()) {
					mesmoNumDeVotos = false;
					break;
				}
			}

			if (!mesmoNumDeVotos) {
				return true;
			}

			boolean asPreposicoesBatem = true;
			for (Aluno a2 : alunos) {
				if (!proposicoesBatem(proposicoesDoAluno.apply(base), proposicoesDoAluno.apply(a2))) {
					asPreposicoesBatem = false;
					break;
				}
			}

			return !asPreposicoesBatem;
		}
		return true;
	}

	private Optional<Aluno> obterAlunoComMaiorNumPreposicoes() {
		int numPreposicoes = 0;
		Aluno aluno = null;

		if (!this.respostas.isEmpty()) {

			final Function<Aluno, Integer> numProposicoesDoAluno = a -> this.respostas.stream()
					.filter(r -> r.dadaPor(a)).map(RespostaQuestao::getProposicao).collect(Collectors.toList()).size();

			aluno = this.respostas.stream().findFirst().map(RespostaQuestao::getAluno).get();
			numPreposicoes = numProposicoesDoAluno.apply(aluno);

			for (Aluno a2 : this.respostas.stream().map(RespostaQuestao::getAluno).collect(Collectors.toSet())) {

				int numProposicoesA2 = numProposicoesDoAluno.apply(a2);
				if (numProposicoesA2 > numPreposicoes) {
					aluno = a2;
					numPreposicoes = numProposicoesA2;
				}
			}
		}

		return Optional.ofNullable(aluno);
	}

	private boolean proposicoesBatem(List<Proposicao> ps1, List<Proposicao> ps2) {
		return ps1.size() == ps1.stream().filter(ps2::contains).collect(Collectors.toList()).size();
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExecucaoQuestao) {
			return this.getId().equals(((ExecucaoQuestao) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

}
