package action.CRUDAprendizagem;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterQuestao.IManterQuestao;
import modelObjects.Conteudo;
import modelObjects.Disciplina;
import modelObjects.Ementa;
import modelObjects.Questao;
import modelObjects.Referencia;
import modelObjects.Proposicao;

public class ActionManterQuestao extends ActionSupport implements
IManterQuestao, ModelDriven<Questao>, ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Questao questao;
	private String button;
	private Proposicao proposicao;
	
	private File imagemProp;
	private String imagemPropContentType;
	private String imagemPropFileName;
	
	private File userImage;
	private String userImageContentType;
	private String userImageFileName;

	private HttpServletRequest servletRequest;

	@Override
	public Questao getModel() {
		
		if(questao == null){
			
			questao = new Questao();
			questao.setReferencia(new Referencia());
			questao.setConteudo(new Conteudo());
			questao.getConteudo().setEmenta(new Ementa());
			questao.getConteudo().getEmenta().setDisciplina(new Disciplina());
			
		}
		
		proposicao = new Proposicao();
		
		// TODO Auto-generated method stub
		return questao;
	}
	
	@Override
	public void validate(){
		
		//única verificação para excluir é possuir o id
			if("Excluir".equals(getButton())){
				
				if( questao.getIdQuestao() == null || questao.getIdQuestao().equals(0) )
					addActionMessage("Você deve selecionar o conteudo para poder exclui-lo!");	
				
				return;
			}
			System.out.println("enun"+questao.getEnunciado());
			
			if(questao.getEnunciado() == null || questao.getEnunciado().isEmpty()){
				addFieldError("questao.enunciado", "O campo enunciado não pode ser vazio!");
				return;
			}
			
			System.out.println("dificuldade"+questao.getDificuldade());
			
			if(questao.getDificuldade() == null || questao.getEnunciado().isEmpty()){
				addFieldError("questao.dificuldade", "O campo dificuldade não pode ser vazio!");
				return;
			}
			
			System.out.println("peso"+questao.getPeso());
			
			if(questao.getPeso() == null || questao.getEnunciado().equals(0)){
				addFieldError("questao.peso", "O campo peso não pode ser vazio!");
				return;
			}
			
			System.out.println("ref"+questao.getReferencia().getIdReferencia());
			
			if(questao.getReferencia().getIdReferencia() == null || 
					questao.getReferencia().getIdReferencia().equals(0)){
				addFieldError("questao.referencia.autor", "O campo referencia não pode ser vazio!");
				return;
			}
			
			System.out.println("idCon"+questao.getConteudo().getIdConteudo());
			
			if(questao.getConteudo().getIdConteudo().equals(0) || 
					questao.getConteudo().getIdConteudo().equals(null)){
				
				addFieldError("questao.conteudo.nome", "O campo conteúdo não pode ser vazio!");
				return;
			}
			
			System.out.println("edEment"+questao.getConteudo().getEmenta().getIdEmenta());
			
			if(questao.getConteudo().getEmenta().getIdEmenta().equals(0) || 
					questao.getConteudo().getEmenta().getIdEmenta().equals(null)){
				
				addFieldError("questao.conteudo.ementa.nome", "O campo ementa não pode ser vazio!");
				return;
			}
			
			System.out.println("idDisc"+questao.getConteudo().getEmenta().getDisciplina().getIdDisicplina());
			
			if(questao.getConteudo().getEmenta().getDisciplina().getIdDisicplina().equals(0) || 
					questao.getConteudo().getEmenta().getDisciplina().getIdDisicplina().equals(null)){
				addFieldError("questao.conteudo.ementa.disciplina.nome", "O campo disciplina não pode ser vazio!");
				return;
			}
		
	}
	@Override
	public String execute(){
		
		if("Adicionar Proposição".equals(getButton())){
			return this.salvarProposicao();
		}
		
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(questao.getIdQuestao() == null || questao.getIdQuestao().equals(0)){
			return this.Insert();
		}
		else{
			return this.Update();
			
		}
	}

	private String Update() {
		
		if(!userImageFileName.isEmpty()){
			
			String filePath ;
			try {
				filePath = servletRequest.getSession().getServletContext().getRealPath("/");
				System.out.println("Server path:" + filePath);
				File fileToCreate = new File(filePath, this.userImageFileName);
				
				FileUtils.copyFile(this.userImage, fileToCreate);
			} catch (Exception e) {
				e.printStackTrace();
				addActionError(e.getMessage());
	
				return INPUT;
			}
		
		 	questao.setImagem(filePath);
		}
		
		
		if(mantQuestao.update(questao)){
			addActionMessage("Questão modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a modificação");
		return ERROR;
	}
	
	private String salvarProposicao(){
		
		System.out.println("to aqui");
			
		if(!userImageFileName.isEmpty()){
			
			String filePath ;
			try {
				filePath = servletRequest.getSession().getServletContext().getRealPath("/");
				System.out.println("Server path:" + filePath);
				File fileToCreate = new File(filePath, this.userImageFileName);
				
				FileUtils.copyFile(this.userImage, fileToCreate);
			} catch (Exception e) {
				e.printStackTrace();
				addActionError(e.getMessage());
	
				return INPUT;
			}
		
		 	this.proposicao.setImagem(filePath);
		}
		
		this.questao.getProposicoes().add(proposicao);
		this.proposicao = new Proposicao();
		
		return SUCCESS;
	}

	private void Clean() {
		
		this.button = "";
		this.questao.getConteudo().setNome("");
		this.questao.getConteudo().getEmenta().setIdEmenta(0);
		this.questao.getConteudo().getEmenta().setNome("");
		this.questao.getConteudo().getEmenta().getDisciplina().setIdDisicplina(0);
		this.questao.getConteudo().getEmenta().getDisciplina().setNome("");
		this.questao.setDificuldade("");
		this.questao.setEnunciado("");
		this.questao.setIdQuestao(0);
		this.questao.setImagem("");
		this.questao.setPeso(0);
		this.questao.getProposicoes().clear();
		this.questao.getReferencia().setIdReferencia(0);
		this.questao.getReferencia().setAutor("");
		this.userImageFileName = "";	
		
	}

	private String Insert() {
		
		if(!userImageFileName.isEmpty()){
			
			String filePath ;
			try {
				filePath = servletRequest.getSession().getServletContext().getRealPath("/");
				System.out.println("Server path:" + filePath);
				File fileToCreate = new File(filePath, this.userImageFileName);
				
				FileUtils.copyFile(this.userImage, fileToCreate);
			} catch (Exception e) {
				e.printStackTrace();
				addActionError(e.getMessage());
	
				return INPUT;
			}
		
		 	questao.setImagem(filePath);
		}
		
		if(mantQuestao.insert(questao)){
			addActionMessage("Questão inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a inserção");
		return ERROR;
	}

	private String Delete() {
		if(mantQuestao.delete(questao)){
			addActionMessage("Questão removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("Não foi possível realizar a remoção");
		return ERROR;
	}
	
	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		
	}

	public File getUserImage() {
		return userImage;
	}

	public void setUserImage(File userImage) {
		this.userImage = userImage;
	}

	public String getUserImageContentType() {
		return userImageContentType;
	}

	public void setUserImageContentType(String userImageContentType) {
		this.userImageContentType = userImageContentType;
	}

	public String getUserImageFileName() {
		return userImageFileName;
	}

	public void setUserImageFileName(String userImageFileName) {
		this.userImageFileName = userImageFileName;
	}

	public Proposicao getProposicao() {
		return proposicao;
	}

	public void setProposicao(Proposicao proposicao) {
		this.proposicao = proposicao;
	}

	public File getImagemProp() {
		return imagemProp;
	}

	public void setImagemProp(File imagemProp) {
		this.imagemProp = imagemProp;
	}

	public String getImagemPropContentType() {
		return imagemPropContentType;
	}

	public void setImagemPropContentType(String imagemPropContentType) {
		this.imagemPropContentType = imagemPropContentType;
	}

	public String getImagemPropFileName() {
		return imagemPropFileName;
	}

	public void setImagemPropFileName(String imagemPropFileName) {
		this.imagemPropFileName = imagemPropFileName;
	}

}
