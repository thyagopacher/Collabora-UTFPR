package action.CRUDAprendizagem;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterTurma.IManterTurma;
import modelObjects.Aluno;
import modelObjects.Disciplina;
import modelObjects.Professor;
import modelObjects.Turma;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Que oferece o servi�o de CRUD para a turma (IManterTurma)
 * 	- Que oferece o seri�o interface com a vis�o (ModelDriven)
 *  - servi�o de pesquisa:
 *  		- pesquisar todos os alunos de uma turma	(IPesquisarAlunosPorTurma)
 *  		- pesquisar professores 					(IPesquisarProfessor)
 *  		- pesqisar disciplina 						(IPesquisarDisciplina)
 * 
 *  A classe extende a ActionSupport, classe base para as classes de a��es no struts2
 * */
public class ActionManterTurma 

	extends ActionSupport 
	
	implements 	IManterTurma,
				ModelDriven<Turma>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Turma turma = new Turma();
	private String idProf;
	private String idDisc;
	private String nomeProfessor;
	private String nomeDisciplina;
	private String button;
	private String idAlunos;
	
	@Override
	public void validate() {
		
		//�nica verifica��o para excluir � possuir o id
		if("Excluir".equals(getButton())){
			
			if( this.turma.getId() == null || this.turma.getId().equals(0))
				addFieldError("turma.codigo","Voc� deve selecionar a turma para poder exclui-la!");	
			
			return;
		}
		
		if(turma.getCodigo().isEmpty()){
			addFieldError("turma.codigo", "O campo c�digo n�o pode ser vazio!");
			return ;
		}
		
		if(idProf.isEmpty()){
			addFieldError("nomeProfessor", "Voc� deve selecionar um professor!");
			return ;
		}
		
		if(idDisc.isEmpty()){
			addFieldError("nomeDisciplina", "Voc� deve selecionar uma disciplina!");
			return ;
		}
		
		if(!this.validarId()){
			addFieldError("nomeDisciplina", "Devem ser selecionados alunos para a turma");
			return ;
		}
	}
	
	//retorna verdadeiro se existirem ids
	//retorna falso caso contrario
	private boolean validarId(){
		
		String[] aux = idAlunos.split(":");
		
		return !aux[1].isEmpty();
		
	}
	
	private void criarTurma(){
		
		turma.setDisciplina(new Disciplina());
		turma.setProfessor(new Professor());
		
		turma.getDisciplina().setIdDisicplina(Integer.parseInt(this.idDisc));
		turma.getProfessor().setId(Integer.parseInt(this.idProf));
		
		ArrayList<Aluno> alunos = new ArrayList<>();
		
		String[] aux = idAlunos.split(":");
		aux = aux[1].split(",");
		
		for(String id : aux){
			
			Aluno aluno = new Aluno();
			aluno.setId(Integer.parseInt(id));
			
			alunos.add(aluno);
			
		}
		
		turma.setAluno(alunos);
	}
	
	@Override
	public String execute(){
		
		this.criarTurma();
		
		if("Excluir".equals(getButton()))
			return this.Delete();
		
		if(this.turma.getId() == null || this.turma.getId().equals(0))
			return this.Insert();
		else
			return this.Update();
		
	}

	/*
	 * 			UPDATE
	 * 
	 * 		Tenta realizar a modifica��o da turma:
	 * 	-caso for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de A��o, limpa o model turma e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a modifica��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Update() {

		if(mantTurma.modificar(turma)){
			addActionMessage("Turma modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	
	/*
	 * 			INSERT
	 * 
	 * 		Tenta realizar a inser��o da turma:
	 * 	-caso for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de A��o, limpa o model turma e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a inser��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Insert() {
		if(mantTurma.inserir(turma)){
			addActionMessage("Turma inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
	}

	private void Clean() {
		idProf = "";
		idDisc= "";
		nomeDisciplina = "";
		nomeProfessor = "";
		button= "";
		idAlunos= "";
		this.turma.setCodigo("");
		this.turma.setAluno(null);
		this.turma.setDisciplina(null);
		this.turma.setId(0);
	}
	/*
	 * 			DELETE
	 * 
	 * 		Tenta realizar a remo��o da turma:
	 * 	-caso for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de A��o, limpa o model turma e retorna SUCCESS
	 * 
	 * 	-caso n�o for poss�vel realizar a remo��o
	 * 		-Adicona uma mensagem de erro de A��o e retorna ERROR
	 * */
	private String Delete() {
		if(mantTurma.remover(turma)){
			addActionMessage("Turma removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}
		
		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public Turma getModel() {
		return turma;
	}



	public String getButton() {
		return button;
	}



	public void setButton(String button) {
		this.button = button;
	}

	public String getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(String idAlunos) {
		this.idAlunos = idAlunos;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getIdProf() {
		return idProf;
	}

	public void setIdProf(String idProf) {
		this.idProf = idProf;
	}

	public String getIdDisc() {
		return idDisc;
	}

	public void setIdDisc(String idDisc) {
		this.idDisc = idDisc;
	}

}
