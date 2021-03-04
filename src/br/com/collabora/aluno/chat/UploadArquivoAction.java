package br.com.collabora.aluno.chat;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import com.opensymphony.xwork2.ActionSupport;

import br.com.collabora.facades.FacadeEJB;
import br.com.collabora.facades.ManipuladorFacades;
import br.com.collabora.modelo.colaboracoes.anexos.Extensao;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;
import br.com.collabora.sessoes.alunos.chat.ControladorChats;

public class UploadArquivoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private final String EXTENSAO_INVALIDA = "Extensão inválida. Válidas: %s ";

	private File file;
	private String contentType;
	private String filename;

	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}

	public String execute() {
		try {
			final String idSessao = ManipuladorFacades.getControlador(ControladorSessaoAluno.class).getId();

			final Optional<Extensao> extensao = Extensao.obterPorContentType(contentType);

			if (!extensao.isPresent()) {
				addActionError(String.format(EXTENSAO_INVALIDA, Arrays.asList(Extensao.values())));

				return SUCCESS;
			}

			FacadeEJB.obterPelaClasseDoBean(ControladorChats.class).incluirColaboracaoPorArquivo(idSessao, file,
					contentType, filename);

			return SUCCESS;
		} finally {
			this.file = null;
			this.contentType = null;
			this.filename = null;
		}
	}
}
