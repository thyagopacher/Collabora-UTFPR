package br.com.collabora.filtros.alunos;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

@WebFilter("/Aluno/*")
public class FiltroSessaoAluno implements Filter {

	private final String PAGINA_ERRO_LOGIN = "acessoNegado.jsp";

	private @Inject ControladorSessaoAluno sessaoAluno;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (sessaoAluno.existeAlunoLogado()) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(
					String.format("%s/%s", ((HttpServletRequest) request).getContextPath(), PAGINA_ERRO_LOGIN));
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
