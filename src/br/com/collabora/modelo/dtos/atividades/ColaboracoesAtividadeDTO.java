package br.com.collabora.modelo.dtos.atividades;

import java.util.Date;

public class ColaboracoesAtividadeDTO {

	private final Integer idAtividade;

	private final Date fimUltimaExec;

	private final Integer numLinks;

	private final Integer numArquivos;

	public ColaboracoesAtividadeDTO(Integer idAtividade, Date fimUltimaExec, Integer numLinks, Integer numArquivos) {
		this.idAtividade = idAtividade;
		this.fimUltimaExec = fimUltimaExec;
		this.numLinks = numLinks;
		this.numArquivos = numArquivos;
	}

	public Integer getIdAtividade() {
		return idAtividade;
	}

	public Date getFimUltimaExec() {
		return fimUltimaExec;
	}

	public Integer getNumLinks() {
		return numLinks;
	}

	public Integer getNumArquivos() {
		return numArquivos;
	}

}
