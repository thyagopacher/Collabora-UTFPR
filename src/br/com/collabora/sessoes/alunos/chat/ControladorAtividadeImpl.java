package br.com.collabora.sessoes.alunos.chat;

import java.io.File;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import br.com.collabora.builders.colaboracoes.ColaboracaoBuilder;
import br.com.collabora.builders.colaboracoes.ColaboracaoBuilderPadrao;
import br.com.collabora.core.Logger;
import br.com.collabora.dao.atividades.AtividadeDao;
import br.com.collabora.dao.execucoes.colaboracoes.ColaboracaoDao;
import br.com.collabora.dao.execucoes.questoes.ExecucaoQuestaoDao;
import br.com.collabora.dao.questoes.proposicoes.ProposicaoDao;
import br.com.collabora.factories.colaboracoes.JsonColaboracaoFactory;
import br.com.collabora.modelo.colaboracoes.sessoes.ColaboracaoDeSessao;
import br.com.collabora.modelo.sessao.chat.AtividadeGrupo;
import br.com.collabora.sessoes.alunos.ControladorSessoesAlunos;
import br.com.collabora.websocket.mensagens.alunos.RespostaAto;
import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.Colaboracao;
import modelObjects.ExecucaoQuestao;
import modelObjects.FeedbackQuestao;
import modelObjects.Grupo;
import modelObjects.Proposicao;
import modelObjects.Questao;
import modelObjects.RespostaQuestao;
import modelObjects.StatusQuestao;

@Stateful
public class ControladorAtividadeImpl implements ControladorAtividade {

	private static final long serialVersionUID = 1L;

	private final int TOTAL_SEGUNDOS_FASE_1 = 1801;

	private final int TOTAL_SEGUNDOS_EXTRAS_FASE_2 = 300;

	private final String id = UUID.randomUUID().toString();

	private Grupo grupo;

	private Optional<Atividade> atividade;

	private Optional<Questao> questao = Optional.empty();

	private ExecucaoQuestao execucaoQuestao;

	private Duration duracao = Duration.ofSeconds(TOTAL_SEGUNDOS_FASE_1);

	private Duration duracaoJaExecutada = null;

	private @Inject ControladorSessoesAlunos controladorSessoesAlunos;

	private @Inject AtividadeDao atividadeDao;

	private @Inject ExecucaoQuestaoDao execucaoQuestaoDao;

	private @Inject ProposicaoDao proposicaoDao;

	private @Inject ColaboracaoDao colaboracaoDao;

	private Set<AtividadeObserver> observadores = new HashSet<>();

	private final ColaboracaoBuilder colaboracaoBuilder = new ColaboracaoBuilderPadrao();

	private boolean primeiroPeriodoExpirou = false;

	private boolean segundoPeriodoExpirou = false;

	private boolean empatou = false;

	private JsonObject feedbackQuestao = null;

	private JsonObject feedbackAtividade = null;

	@Asynchronous
	@Override
	public void notificarTempoAosAlunos() {
		long duracaoTotal = Duration.ofSeconds(duracao.getSeconds() - duracaoJaExecutada.getSeconds()).getSeconds();

		if (notificarFeedback(duracaoTotal)) {
			return;
		}

		if (!questao.isPresent()) {
			return;
		}

		if (executarQuestaoAberta(duracaoTotal)) {
			return;
		}

		// finalizarExecucaoQuestao();
	}

	public boolean notificarFeedback(long duracaoTotal) {
		if (feedbackQuestao != null) {
			controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), feedbackQuestao);
			return true;
		} else if (feedbackAtividade != null) {
			controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), feedbackAtividade);
			return true;
		}
		return false;
	}

	private boolean executarQuestaoAberta(long duracaoTotal) {
		if (controladorSessoesAlunos.obterNumDeAlunosLogados(grupo) > 1) {

			if (expirou()) {
				finalizarExecucaoQuestao();
			}

			duracao = duracao.minusSeconds(1);
			notificarTempoAosAlunos(duracaoTotal);
			return true;
		}
		return false;
	}

	private boolean expirou() {
		return (primeiroPeriodoExpirou && !empatou) || segundoPeriodoExpirou;
	}

	private void reiniciar() {
		feedbackQuestao = null;

		this.questao = buscarQuestaoEmAberto();

		if (questao.isPresent()) {
			primeiroPeriodoExpirou = false;
			segundoPeriodoExpirou = false;
			empatou = false;

			duracao = Duration.ofSeconds(TOTAL_SEGUNDOS_FASE_1);

			notificarNovaExecucaoAosAlunos();
		} else {
			feedbackAtividade = execucaoQuestaoDao.obterFeedbackAtividade(grupo, atividade.get()).toJson();
		}
	}

	// private void finalizar() {
	// execucaoQuestaoDao.finalizarExecucao(this.execucaoQuestao.getId());
	//
	// }

	@Override
	public void inicializar(Grupo grupo, Atividade atividade) {
		this.atividade = Optional.ofNullable(atividade);
		this.grupo = grupo;
		this.questao = buscarQuestaoEmAberto();
	}

	private Optional<Questao> buscarQuestaoEmAberto() {
		if (atividade.isPresent()) {

			final Optional<Questao> questao = atividadeDao.obterProximaQuestaoParaExecucao(this.grupo, atividade.get());

			questao.ifPresent(this::iniciarNovaExecucao);

			return questao;
		}
		return Optional.empty();
	}

	private void iniciarNovaExecucao(Questao questao) {
		final List<Duration> periodosJaExecutados = execucaoQuestaoDao.buscarPeriodosJaExecutados(this.grupo, questao);

		this.duracaoJaExecutada = Duration.ofSeconds(periodosJaExecutados.isEmpty() ? 0
				: periodosJaExecutados.stream().mapToLong(Duration::getSeconds).sum());

		this.execucaoQuestaoDao.inserir((this.execucaoQuestao = new ExecucaoQuestao(this.grupo, questao)));
	}

	private void notificarNovaExecucaoAosAlunos() {
		final JsonArrayBuilder proposicoesBuilder = JsonProvider.provider().createArrayBuilder();

		final Comparator<Proposicao> ordenador = (p1, p2) -> p1.getId().compareTo(p2.getId());

		int identificacao = 97;
		for (Proposicao p : execucaoQuestao.getReferencia().getProposicoes().stream().sorted(ordenador)
				.collect(Collectors.toList())) {
			final char proposicao = (char) identificacao;

			final JsonObjectBuilder proposicaoBuilder = JsonProvider.provider().createObjectBuilder();
			proposicaoBuilder.add("id", p.getIdProposicao());
			proposicaoBuilder.add("proposicao", String.format("%s", proposicao));
			proposicaoBuilder.add("enunciado", p.getEnunciado());

			proposicoesBuilder.add(proposicaoBuilder.build());

			++identificacao;
		}

		final JsonObjectBuilder questaoBuilder = JsonProvider.provider().createObjectBuilder();
		questaoBuilder.add("enunciado", execucaoQuestao.getReferencia().getEnunciado());
		questaoBuilder.add("proposicoes", proposicoesBuilder.build());

		final JsonObjectBuilder objectBuilder = JsonProvider.provider().createObjectBuilder();
		objectBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.TROCAR_QUESTAO.toString());
		objectBuilder.add("questao", questaoBuilder.build());

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), objectBuilder.build());

	}

	public void notificarTempoAosAlunos(long total) {
		primeiroPeriodoExpirou = total < 0;
		segundoPeriodoExpirou = total + TOTAL_SEGUNDOS_EXTRAS_FASE_2 <= 0;

		long horas = total / 3600;
		long minutos = (total - (horas * 3600)) / 60;
		long segundos = (total - (horas * 3600) - (minutos * 60));

		final JsonObjectBuilder objectBuilder = JsonProvider.provider().createObjectBuilder();
		objectBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.TEMPO_DURACAO_ATIVIDADE.toString());
		objectBuilder.add("horas", horas);
		objectBuilder.add("segundos", segundos);
		objectBuilder.add("minutos", minutos);

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), objectBuilder.build());
	}

	@Override
	public void incluirColaboracaoTextual(String idSessao, String mensagem) {
		final boolean finalizada = feedbackQuestao != null || feedbackAtividade != null;

		if (finalizada) {
			return;
		}

		final Optional<Aluno> responsavel = this.controladorSessoesAlunos.obterAluno(idSessao);

		final Colaboracao colaboracao = responsavel.isPresent() ? colaboracaoBuilder.reiniciar()
				.adicionarMensagem(mensagem).adicionarIdSessao(idSessao)
				.adicionarIdExecucaoQuestao(execucaoQuestao.getId()).adicionarResponsavel(responsavel.get()).construir()
				: null;

		if (responsavel.isPresent() && this.colaboracaoDao.inserir(colaboracao)) {
			execucaoQuestao.getColaboracoes().add(new ColaboracaoDeSessao(idSessao, colaboracao));

			enviarNotificacaoDeColaboracao(idSessao);
		}
	}

	@Override
	public void incluirColaboracaoPorArquivo(String idSessao, File file, String contentType, String nomeArquivo) {
		final boolean finalizada = feedbackQuestao != null || feedbackAtividade != null;

		if (finalizada) {
			return;
		}

		final Optional<Aluno> responsavel = this.controladorSessoesAlunos.obterAluno(idSessao);

		final Colaboracao colaboracao = responsavel.isPresent() ? colaboracaoBuilder.reiniciar()
				.adicionarArquivo(file, contentType, nomeArquivo).adicionarIdSessao(idSessao)
				.adicionarIdExecucaoQuestao(execucaoQuestao.getId()).adicionarResponsavel(responsavel.get()).construir()
				: null;

		if (responsavel.isPresent() && this.colaboracaoDao.inserir(colaboracao)) {
			execucaoQuestao.getColaboracoes().add(new ColaboracaoDeSessao(idSessao, colaboracao));

			enviarNotificacaoDeColaboracao(idSessao);
		}
	}

	private void enviarNotificacaoDeColaboracao(String idSessao) {

		final JsonArrayBuilder arrayBuilder = JsonProvider.provider().createArrayBuilder();
		execucaoQuestao.getColaboracoes().stream().sorted((o1, o2) -> {
			return o1.getColaboracao().getDataHoraOcorrencia().compareTo(o2.getColaboracao().getDataHoraOcorrencia());
		}).map(col -> {
			return JsonColaboracaoFactory.obterPorTipoColaboracao(col.getColaboracao().getClass())
					.criar(col.getIdSessao(), col.getColaboracao());
		}).forEach(arrayBuilder::add);

		final JsonObjectBuilder objectBuilder = JsonProvider.provider().createObjectBuilder();
		objectBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.COLABORACAO.toString());
		objectBuilder.add("registros", arrayBuilder.build());

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			Logger.getInstance().logError("Exception - Ao pausar 200mills para envio de Colaboração", e);
		}

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), objectBuilder.build());
	}

	@Override
	public void responderQuestao(String idSessao, List<String> idsProposicoes) {
		final boolean finalizada = feedbackQuestao != null || feedbackAtividade != null;

		if (finalizada) {
			return;
		}

		final List<Proposicao> proposicoes = proposicaoDao.listarPorIds(idsProposicoes);
		final Optional<Aluno> aluno = this.controladorSessoesAlunos.obterAluno(idSessao);

		if (proposicoes.isEmpty() || !aluno.isPresent()) {
			return;
		}

		if (idsProposicoes.size() != proposicoes.size()) {
			throw new RuntimeException(
					String.format("Nem todos os ids de proposição (%s) foram encontrados no banco (%s)", idsProposicoes,
							proposicoes.stream().map(Proposicao::getId).collect(Collectors.toList())));
		}

		synchronized (execucaoQuestao) {
			execucaoQuestao.incluirResposta(aluno.get(), proposicoes);

			if (!controladorSessoesAlunos.obterAlunosLogados(grupo).stream()
					.allMatch(execucaoQuestao::alunoTemResposta)) {
				return;
			}

			final boolean semConsenso = execucaoQuestao.semConsenso();

			if (!segundoPeriodoExpirou && semConsenso) {

				notificarEmpateDeQuestao();

				execucaoQuestao.removerRespostas();

				empatou = true;

				return;
			}

			finalizarExecucaoQuestao();
		}
	}

	private void finalizarExecucaoQuestao() {
		execucaoQuestao.setStatusQuestao(execucaoQuestaoDao.atualizarRespostasEMudarStatus(execucaoQuestao));

		feedbackQuestao = new FeedbackQuestao(this.questao.get(), execucaoQuestao).toJson();

		questao = Optional.empty();
	}

	@Override
	public void finalizarQuestao() {
		if (controladorSessoesAlunos.obterAlunosLogados(grupo).stream().allMatch(execucaoQuestao::alunoTemResposta)
				|| (!StatusQuestao.EM_ABERTO.equals(execucaoQuestao.getStatusQuestao()) && expirou())) {
			reiniciar();
		}
	}

	@Override
	public void finalizarAtividade() {
		removerAtividadeERedirecionar();
	}

	private void removerAtividadeERedirecionar() {
		feedbackQuestao = null;
		feedbackAtividade = null;
		questao = Optional.empty();

		final JsonObjectBuilder mensagemBuilder = JsonProvider.provider().createObjectBuilder();
		mensagemBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.REDIRECIONAR_APOS_ATIVIDADE.toString());

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), mensagemBuilder.build());

		observadores.forEach(o -> o.notificarFechamento(id));
	}

	private void notificarEmpateDeQuestao() {
		final JsonObjectBuilder mensagemBuilder = JsonProvider.provider().createObjectBuilder();
		mensagemBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.NOTIFICAR_EMPATE.toString());

		final List<Proposicao> proposicoes = questao.get().getProposicoes().stream()
				.sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).collect(Collectors.toList());

		final Function<Aluno, List<Proposicao>> proposicoesAlunos = a -> execucaoQuestao.getRespostas().stream()
				.filter(r -> r.dadaPor(a)).map(RespostaQuestao::getProposicao).collect(Collectors.toList());

		final JsonArrayBuilder alunosBuilder = JsonProvider.provider().createArrayBuilder();

		for (Aluno a : execucaoQuestao.getRespostas().stream().map(RespostaQuestao::getAluno).distinct()
				.collect(Collectors.toList())) {
			final JsonArrayBuilder votosBuilder = JsonProvider.provider().createArrayBuilder();

			proposicoesAlunos.apply(a).stream().map(proposicoes::indexOf).map(indice -> (char) (indice + 97))
					.map(identificacao -> identificacao + "").forEach(identificacao -> votosBuilder.add(identificacao));

			final JsonObjectBuilder alunoBuilder = JsonProvider.provider().createObjectBuilder();
			alunoBuilder.add("aluno", a.getNome());
			alunoBuilder.add("votos", votosBuilder.build());

			alunosBuilder.add(alunoBuilder.build());
		}

		mensagemBuilder.add("alunos", alunosBuilder.build());

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), mensagemBuilder.build());
	}

	@Override
	public void notificarInfoIniciais(String idSessao) {

		enviarNotificacaoDeColaboracao(idSessao);

		notificarNovaExecucaoAosAlunos();
	}

	public boolean temAtividade() {
		return atividade.isPresent();
	}

	public boolean temGrupo(Aluno aluno) {
		return this.getGrupo().getAlunos().stream().filter(aluno::equals).findAny().isPresent();
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public Optional<Atividade> getAtividade() {
		return atividade;
	}

	@Override
	public boolean mesmoGrupo(Grupo grupo) {
		return this.grupo.equals(grupo);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AtividadeGrupo) {
			return this.getGrupo().getId().equals(((AtividadeGrupo) obj).getGrupo().getId());
		}
		return false;
	}

	@Override
	public void incluirObservador(AtividadeObserver observador) {
		observadores.add(observador);
	}

	@Override
	public String getId() {
		return id;
	}

}
