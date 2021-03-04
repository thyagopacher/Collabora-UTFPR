package br.com.collabora.sessoes.alunos.chat;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Asynchronous;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

import br.com.collabora.dao.atividades.AtividadeDao;
import br.com.collabora.modelo.sessao.chat.inicio.AlunoNaoAtivo;
import br.com.collabora.modelo.sessao.chat.inicio.AlunoNaoTemGrupo;
import br.com.collabora.modelo.sessao.chat.inicio.AtividadeInexistente;
import br.com.collabora.modelo.sessao.chat.inicio.AtividadeIniciada;
import br.com.collabora.modelo.sessao.chat.inicio.GrupoComAtividadeAtiva;
import br.com.collabora.modelo.sessao.chat.inicio.GrupoInsuficiente;
import br.com.collabora.modelo.sessao.chat.inicio.RequisicaoInicioAtividade;
import br.com.collabora.modelo.sessao.chat.inicio.RespostaInicioAtividade;
import br.com.collabora.sessoes.alunos.ControladorSessoesAlunos;
import br.com.collabora.websocket.mensagens.alunos.RespostaAto;
import modelObjects.Atividade;
import modelObjects.Grupo;

@Singleton
@DependsOn("ControladorSessoesAlunosImpl")
public class ControladorChatsImpl implements ControladorChats {

	private static final long serialVersionUID = 1L;

	private @Inject ControladorSessoesAlunos controladorSessoesAlunos;

	private @Inject AtividadeDao atividadeDao;

	private @Inject Instance<ControladorAtividade> geradorControladorAtividades;

	private final Set<ControladorAtividade> controladoresAtividades = new HashSet<>();

	@Schedule(hour = "*", minute = "*", second = "*", persistent = false)
	public void contarTempo() {
		synchronized (controladoresAtividades) {
			for (ControladorAtividade ca : controladoresAtividades) {
				try {
					ca.notificarTempoAosAlunos();
				} catch (Exception e) {
					controladoresAtividades.remove(ca);
				}
			}
		}
	}

	public RespostaInicioAtividade iniciarAtividade(RequisicaoInicioAtividade req) {
		synchronized (this) {
			final Optional<Atividade> atividade = atividadeDao.obter(String.valueOf(req.getIdAtividade()));

			if (!atividade.isPresent()) {
				return new AtividadeInexistente(req.getIdSessao());
			}

			if (!controladorSessoesAlunos.estaAtivaETemGrupoEAluno(req.getIdSessao())) {
				return new AlunoNaoAtivo(req.getIdSessao());
			}

			final Optional<Grupo> grupo = controladorSessoesAlunos.obterGrupo(req.getIdSessao());

			if (!grupo.isPresent()) {
				return new AlunoNaoTemGrupo(req.getIdSessao());
			}

			if (controladorSessoesAlunos.obterNumDeAlunosLogados(grupo.get()) <= 1) {
				return new GrupoInsuficiente(req.getIdSessao());
			}

			if (controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo.get())).findFirst().isPresent()) {
				return iniciarAtividadeDeGrupoPresente(req.getIdSessao(), grupo.get(), atividade.get());
			}

			return criarNovoGrupoEInicarAtividade(req.getIdSessao(), grupo.get(), atividade.get());
		}
	}

	private RespostaInicioAtividade criarNovoGrupoEInicarAtividade(String idSessao, Grupo grupo, Atividade atividade) {

		final ControladorAtividade ca = geradorControladorAtividades.get();
		ca.incluirObservador(this);
		ca.inicializar(grupo, atividade);

		synchronized (this.controladoresAtividades) {
			this.controladoresAtividades.add(ca);
		}

		return notificarGrupo(new AtividadeIniciada(idSessao, String.valueOf(atividade.getIdAtividade())), grupo);
	}

	private RespostaInicioAtividade iniciarAtividadeDeGrupoPresente(String idSessao, Grupo grupo, Atividade atividade) {

		synchronized (this.controladoresAtividades) {
			final ControladorAtividade atividadeGrupo = controladoresAtividades.stream()
					.filter(ag -> ag.mesmoGrupo(grupo)).findFirst().get();

			if (atividadeGrupo.temAtividade()) {

				return notificarGrupo(new GrupoComAtividadeAtiva(idSessao,
						String.valueOf(atividadeGrupo.getAtividade().get().getIdAtividade())), grupo);
			}
		}

		return notificarGrupo(new AtividadeIniciada(idSessao, atividade.getId()), grupo);
	}

	private RespostaInicioAtividade notificarGrupo(RespostaInicioAtividade resposta, Grupo grupo) {

		final JsonObjectBuilder objectBuilder = JsonProvider.provider().createObjectBuilder();

		objectBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.REDIRECIONAMENTO_PARA_CHAT.toString());
		objectBuilder.add("idAtividade", resposta.getAtividade().get());
		objectBuilder.add("idSessao", resposta.getIdSessao());

		this.controladorSessoesAlunos.enviarMensagemAIntegrantes(Optional.of(grupo), objectBuilder.build());

		return resposta;
	}

	@Override
	public void solicitarInfoIniciais(String idSessao) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.notificarInfoIniciais(idSessao);
				});
			}
		});
	}

	@Override
	public void incluirColaboracaoTextual(String idSessao, String mensagem) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.incluirColaboracaoTextual(idSessao, mensagem);
				});
			}
		});
	}

	@Asynchronous
	@Override
	public void incluirColaboracaoPorArquivo(String idSessao, File file, String contentType, String fileName) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.incluirColaboracaoPorArquivo(idSessao, file, contentType, fileName);
				});
			}
		});
	}

	@Override
	public void responderQuestao(String idSessao, List<String> idsProposicoes) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.responderQuestao(idSessao, idsProposicoes);
				});
			}
		});
	}

	@Override
	public void finalizarQuestao(String idSessao) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.finalizarQuestao();
				});
			}
		});
	}

	@Override
	public void finalizarAtividade(String idSessao) {
		controladorSessoesAlunos.obterGrupo(idSessao).ifPresent(grupo -> {
			synchronized (controladoresAtividades) {
				controladoresAtividades.stream().filter(ag -> ag.mesmoGrupo(grupo)).findFirst().ifPresent(ca -> {
					ca.finalizarAtividade();
				});
			}
		});
	}

	@Override
	public void notificarFechamento(String id) {
		synchronized (controladoresAtividades) {
			final Optional<ControladorAtividade> ca = this.controladoresAtividades.stream()
					.filter(controlador -> controlador.getId().equals(id)).findFirst();

			if (ca.isPresent()) {
				controladoresAtividades.remove(ca.get());

			}
		}
	}

}
