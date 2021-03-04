package br.com.collabora.facades;

import java.util.HashMap;
import java.util.Map;

import br.com.collabora.facades.sessao.FacadeSessaoAluno;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

public class ManipuladorFacades {

	private static final Map<Class<? extends Controlador>, Class<? extends ContainerControlador<?>>> facadesPorControladores = new HashMap<>();

	static {
		facadesPorControladores.put(ControladorSessaoAluno.class, FacadeSessaoAluno.class);
	}

	public static <T extends Controlador> T getControlador(Class<T> tipo) {

		if (!facadesPorControladores.containsKey(tipo)) {
			throw new IllegalArgumentException("Controlador e sua Facade devem ser cadastrados nesta classe");
		}

		final Class<? extends ContainerControlador<?>> classeFacade = facadesPorControladores.get(tipo);

		return tipo.cast(((ContainerControlador<?>) FacadeEJB.obterPelaClasseDoBean(classeFacade)).getControlador());
	}

}
