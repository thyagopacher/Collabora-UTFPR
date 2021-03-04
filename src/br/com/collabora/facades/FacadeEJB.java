package br.com.collabora.facades;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FacadeEJB {

	private static final String PADRAO_INICIO_JNDI = "java:global";

	private static final String PROJETO = "Ovaces";

	@SuppressWarnings("unchecked")
	public static <T extends Object> T obterPelaClasseDoBean(Class<T> interfaceDoBean) {
		final String jndi = String.format("%s/%s/%sImpl", PADRAO_INICIO_JNDI, PROJETO, interfaceDoBean.getSimpleName());

		return (T) FacadeEJB.obterPelaJndi(jndi);
	}

	public static Object obterPelaJndi(String jndi) {
		try {
			final Context context = new InitialContext();

			return context.lookup(jndi);
		} catch (NamingException e) {
			throw new RuntimeException(
					String.format("MappedName de EJB não foi definido ou está incorreto [%s]", jndi));
		}
	}

}
