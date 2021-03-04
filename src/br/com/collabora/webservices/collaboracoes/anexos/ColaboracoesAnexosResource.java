package br.com.collabora.webservices.collaboracoes.anexos;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;

import br.com.collabora.dao.execucoes.colaboracoes.ColaboracaoDao;
import br.com.collabora.modelo.colaboracoes.anexos.Extensao;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

@Stateless
@Path("/alunos/colaboracoes/anexos")
public class ColaboracoesAnexosResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject ColaboracaoDao colaboracaoDao;

	private @Inject ControladorSessaoAluno controladorSessaoAluno;

	private StreamingOutput obterPorId(String id, Extensao extensao) {
		return output -> {
			if (controladorSessaoAluno.existeAlunoLogado()) {
				final byte[] conteudo = colaboracaoDao.obterBytesArquivo(id).orElseThrow(NotFoundException::new);
				IOUtils.copy(new ByteArrayInputStream(conteudo), output);
			}
		};
	}

	@Produces("application/jpeg")
	@Path("/obterPorId.jpeg")
	@GET
	public StreamingOutput obterJpegPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.JPEG);
	}

	@Produces("application/png")
	@Path("/obterPorId.png")
	@GET
	public StreamingOutput obterPngPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.PNG);
	}

	@Produces("application/pdf")
	@Path("/obterPorId.pdf")
	@GET
	public StreamingOutput obterPdfPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.PDF);
	}

	@Produces(MediaType.TEXT_PLAIN)
	@Path("/obterPorId.csv")
	@GET
	public StreamingOutput obterCsvPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.CSV);
	}

	@Produces("application/xls")
	@Path("/obterPorId.xls")
	@GET
	public StreamingOutput obterXlsPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.XLS);
	}

	@Produces(MediaType.TEXT_PLAIN)
	@GET
	@Path("/obterPorId.txt")
	public StreamingOutput obterTxtPorId(@QueryParam("id") String id) {
		return obterPorId(id, Extensao.TXT);
	}

}
