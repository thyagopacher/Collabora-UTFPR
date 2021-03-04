	package action;

import com.opensymphony.xwork2.ActionSupport;

import businessService.login.SalvarDados.ISalvarDados;
import businessService.login.arquivoDeDados.IArquivoDeDados;

import java.io.File;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o verifica��o da entrada (ISalvarDados)
 * 	- Que oferece o seri�o de copia dos dados para um lugar n�o temporario (IArquivoDeDados)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionUpload extends ActionSupport implements IArquivoDeDados, ISalvarDados{

	
	private final String MENSAGEM_SELECIONAR_ARQUIVO_TEXTO = "Voc� deve selecionar um arquivo em formato de texto para enviar!";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 		Todos nomes padr�es do frameWork Struts2, para as fun��es de 
	 * enviar arquivos.		
	 * 
	 * */
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String destPath;
	
	
	@Override
	public String execute() {
    	
		return SUCCESS;
        
    }
	
	/*
	 * 
	 * 		salvar Aluno
	 * 
	 * 	Verifica se foi poss�vel copiar o arquivo tempor�rio para um local conhecido
	 * 		 - Se n�o: assiona uma mensagem de erro e retorna erro
	 * 
	 * 	Verifica a entrada passada e configura uma mensagem de a��o ou de erro,
	 * conforme o resultado da valida��o. Retorna Succes no caso em que a verifica��o 
	 * ocorreu sem problemas e Error caso contr�rio.
	 * 
	 * */
	public String salvarAluno(){
		
		File file;
		
		destPath = "C:/Program Files/Apache Software Foundation/Tomcat 8.0/work";
		
		file = this.copiarArquivo(destPath);
		
		if(null == file){
			addActionError(MENSAGEM_SELECIONAR_ARQUIVO_TEXTO);
			return ERROR;
		}
		
		String resultado = salvarDados.validarEntradaAluno(file);
		
		System.out.println(destPath);
		
		switch (resultado) {
		case SUCCESS:
			addActionMessage("Arquivo salvo com sucesso");
			return SUCCESS;
		case ERROR:
			addActionError("O arquivo n�o pode ser salvo");
			return ERROR;
		default:
			addActionError(resultado);
			return ERROR;
		}
		
	}
	
	//mesma l�gica do m�todo acima
	public String salvarProfessor(){
		File file;
		
		destPath = "C:/Program Files/Apache Software Foundation/Tomcat 8.0/work";
		
		file = this.copiarArquivo(destPath);
		
		if(null == file){
			addActionError(MENSAGEM_SELECIONAR_ARQUIVO_TEXTO);
			return ERROR;
		}
		
		String resultado = salvarDados.validarEntradaProfessor(file);
		
		switch (resultado) {
		case SUCCESS:
			addActionMessage("Arquivo salvo com sucesso");
			return SUCCESS;
		case ERROR:
			addActionError("O arquivo n�o pode ser salvo");
			return ERROR;
		default:
			addActionError(resultado);
			return ERROR;
		}
	}
	
	//mesma l�gica do m�todo salvar Aluno
	public String salvarGerente(){
		File file;
		
		destPath = "C:/Program Files/Apache Software Foundation/Tomcat 8.0/work";
		
		file = this.copiarArquivo(destPath);
		
		if(null == file){
			addActionError(MENSAGEM_SELECIONAR_ARQUIVO_TEXTO);
			return ERROR;
		}
		
		String resultado = salvarDados.validarEntradaGerente(file);
		
		System.out.println(resultado);
		
		switch (resultado) {
		case SUCCESS:
			addActionMessage("Arquivo salvo com sucesso");
			return SUCCESS;
		case ERROR:
			addActionError("O arquivo n�o pode ser salvo");
			return ERROR;
		default:
			addActionError(resultado);
			return ERROR;
		}
	}
	
	//Tenta copiar o arquivo tempor�rio criado pelo upload para um lugar fixo e conhecido.
	//retorna o file deste novo arquivo fixo ou nulo caso algo n�o ocorra bem
	private File copiarArquivo(String destino){
		
		if(fileUpload == null || fileUploadFileName == null || destino == null)
			return null;
		
		return arqDados.Copiar(fileUpload, fileUploadFileName, destino);
		
	}

	
	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

}
