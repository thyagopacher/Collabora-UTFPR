package br.com.collabora.modelo.colaboracoes.anexos;

import java.util.Optional;
import java.util.stream.Stream;

public enum Extensao {
	PNG("image/png"), 
	JPEG("image/jpeg"),
	JPG("image/jpg"),
	
	ZIP("application/zip"),
	PDF("application/pdf"),
	
	XLS("application/wps-office.xls"), 
	XLSX("application/wps-office.xlsx"),
	DOC("application/wps-office.doc"),
	DOCX("application/wps-office.docx"),
	PPT("application/wps-office.ppt"),
	PPTX("application/wps-office.pptx"),
	ODS("application/vnd.oasis.opendocument.spreadsheet"),
	ODT("application/vnd.oasis.opendocument.text"),
	
	CSV("text/csv"), 
	TXT("text/plain");
	
	private String contentType;
	private String valor;

	private Extensao(String contentType) {
		this.contentType = contentType;
		this.valor = this.name().toLowerCase();
	}
	
	public static Optional<Extensao> obterPorContentType(String ct){
		return Stream.of(Extensao.values()).filter(e -> e.contentType.equals(ct)).findAny();
	}
	
	
}
