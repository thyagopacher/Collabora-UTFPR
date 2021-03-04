package br.com.collabora.sessoes.alunos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import br.com.collabora.modelo.sessao.SessaoAluno;
import br.com.collabora.modelo.sessao.Situacao;
import br.com.collabora.websocket.mensagens.alunos.RespostaAto;
import modelObjects.Aluno;
import modelObjects.Grupo;

@Startup
@Singleton
public class ControladorSessoesAlunosImpl implements ControladorSessoesAlunos {

	public static final String NOME = ControladorSessoesAlunosImpl.class.getSimpleName();

	private static final long serialVersionUID = 1L;

	private final Set<SessaoAluno> sessoesAtivas = new HashSet<>();

	@Override
	public boolean estaAtiva(String id) {
		synchronized (this) {
			return obterSessaoAtiva(id).isPresent();
		}
	}

	@Override
	public boolean estaAtivaETemGrupoEAluno(String id) {
		synchronized (this) {
			final Optional<SessaoAluno> sa = obterSessaoAtiva(id);

			return sa.isPresent() && sa.get().temAlunoLogado() && sa.get().getGrupo().isPresent();
		}
	}

	@Override
	public void atualizarSessao(String id, Optional<Aluno> aluno, Optional<Grupo> grupo) {
		synchronized (this) {
			if (!estaAtiva(id)) {
				sessoesAtivas.add(new SessaoAluno(id));
			}
			obterSessaoAtiva(id).get().atualizarDados(aluno, grupo);

			System.err.println(String.format("Iniciando sessão: %s", aluno.get().getNome()));
		}
	}

	@Override
	public void removerSessao(String id) {
		synchronized (this) {
			final Optional<SessaoAluno> sa = obterSessaoAtiva(id);
			if (sa.isPresent()) {
				sessoesAtivas.remove(sa.get());

				notificarIntegrantesDoGrupo(sa.get().getGrupo());
			}
		}

	}

	@Override
	public void iniciarSessaoWebSocket(String id, Session sessao) {
		synchronized (this) {
			if (estaAtiva(id)) {
				sessoesAtivas.stream().filter(s -> s.getId().equals(id)).findFirst().get().incluirSessaoWs(sessao);

				notificarIntegrantesDoGrupo(obterSessaoAtiva(id).get().getGrupo());
			}
		}
	}

	@Override
	public void finalizarSessaoWebSocket(Session sessao) {
		synchronized (this) {
			final Optional<SessaoAluno> sessaoAtiva = sessoesAtivas.stream()
					.filter(s -> s.getSessoesWs().contains(sessao)).findFirst();

			if (sessaoAtiva.isPresent()) {
				sessoesAtivas.stream().filter(s -> s.getSessoesWs().contains(sessao)).findFirst()
						.ifPresent(s -> s.removerSessaoWs(sessao));

				notificarIntegrantesDoGrupo(sessaoAtiva.get().getGrupo());
			}
		}
	}

	public void enviarMensagemAIntegrantes(Optional<Grupo> grupo, JsonObject mensagem) {
		synchronized (this) {
			if (grupo.isPresent()) {
				final Map<Aluno, List<Session>> sessoesWsRelacionadasAoGrupo = obterSessoesWsRelacionadasAoGrupo(grupo);

				enviarMensagemATodasAsSessoes(sessoesWsRelacionadasAoGrupo.values().stream()
						.flatMap(sessoes -> sessoes.stream().filter(s -> s.isOpen())).collect(Collectors.toList()),
						mensagem);

			}
		}
	}

	private void notificarIntegrantesDoGrupo(Optional<Grupo> grupo) {
		if (grupo.isPresent()) {
			final Map<Aluno, List<Session>> sessoesWsRelacionadasAoGrupo = obterSessoesWsRelacionadasAoGrupo(grupo);

			final List<JsonObject> mensagens = sessoesWsRelacionadasAoGrupo.keySet().stream()
					.map(this::criarMensagemDeStatus).collect(Collectors.toList());

			final JsonObjectBuilder objectBuilder = JsonProvider.provider().createObjectBuilder();

			objectBuilder.add(RespostaAto.class.getSimpleName(), RespostaAto.ATUALIZACAO_SESSOES.toString());

			objectBuilder.add("alunosDoGrupo", obterNumDeAlunosLogados(grupo.get()));

			final JsonArrayBuilder arrayBuilder = JsonProvider.provider().createArrayBuilder();

			mensagens.forEach(arrayBuilder::add);

			objectBuilder.add("registros", arrayBuilder.build());

			enviarMensagemATodasAsSessoes(
					sessoesWsRelacionadasAoGrupo.values().stream()
							.flatMap(sessoes -> sessoes.stream().filter(s -> s.isOpen())).collect(Collectors.toList()),
					objectBuilder.build());
		}
	}

	private JsonObject criarMensagemDeStatus(Aluno aluno) {
		final String status = sessoesAtivas.stream().filter(sa -> sa.mesmoAluno(aluno)).findAny().isPresent()
				? Situacao.ONLINE.name() : Situacao.OFFLINE.name();

		return JsonProvider.provider().createObjectBuilder().add("nome", aluno.getNome()).add("status", status).build();
	}

	private void enviarMensagemATodasAsSessoes(Collection<Session> sessoesWs, JsonObject message) {
		final Collection<Session> sessoesWSInativas = new ArrayList<>();
		for (Session sessao : sessoesWs) {
			try {
				sessao.getBasicRemote().sendText(message.toString());
			} catch (IOException ex) {
				sessoesWSInativas.add(sessao);
			}
		}
		removerSessoesWsInativas(sessoesWSInativas);
	}

	private void removerSessoesWsInativas(Collection<Session> sessoesWSInativas) {
		synchronized (this) {
			for (Session sWS : sessoesWSInativas) {
				sessoesAtivas.stream().filter(sa -> sa.getSessoesWs().contains(sWS)).findFirst().get()
						.removerSessaoWs(sWS);
			}
		}
	}

	private Map<Aluno, List<Session>> obterSessoesWsRelacionadasAoGrupo(Optional<Grupo> grupo) {
		if (grupo.isPresent()) {
			return grupo.get().getAlunos().stream().collect(Collectors.toMap(a -> a, a -> obterSessoesWsDeAluno(a)));
		}
		return new HashMap<>();
	}

	private List<Session> obterSessoesWsDeAluno(Aluno a) {
		return sessoesAtivas.stream().filter(s -> s.mesmoAluno(a) && s.temSessaoWsAtiva())
				.map(SessaoAluno::getSessoesWs).flatMap(col -> col.stream()).collect(Collectors.toList());
	}

	public Optional<SessaoAluno> obterSessaoAtiva(String id) {
		return sessoesAtivas.stream().filter(s -> s.getId().equals(id)).findFirst();
	}

	@Override
	public Optional<Grupo> obterGrupo(String id) {
		synchronized (this) {
			return obterSessaoAtiva(id).filter(sa -> sa != null).flatMap(sa -> sa.getGrupo());
		}
	}

	@Override
	public Optional<Aluno> obterAluno(String id) {
		synchronized (this) {
			return obterSessaoAtiva(id).filter(sa -> sa != null).flatMap(sa -> Optional.ofNullable(sa.getAluno()));
		}
	}

	public long obterNumDeAlunosLogados(Grupo g) {
		return obterAlunosLogados(g).stream().count();
	}

	public Set<Aluno> obterAlunosLogados(Grupo g) {
		synchronized (this) {
			return this.sessoesAtivas.stream()
					.filter(sa -> sa.temAlunoLogado() && sa.getGrupo().isPresent() && sa.getGrupo().get().equals(g))
					.collect(Collectors.groupingBy(sa -> sa.getAluno())).keySet();
		}
	}

}
