package action.CRUDAprendizagem;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import businessService.CRUDAprendizagem.manterGrupo.IManterGrupo;
import businessService.login.pesquisarAluno.IPesquisarAluno;
import businessService.login.pesquisarAluno.PesqAluno;
import modelObjects.Aluno;
import modelObjects.Atividade;
import modelObjects.Grupo;
import modelObjects.Turma;

public class ActionManterGrupo extends ActionSupport implements IManterGrupo, ModelDriven<Grupo>, IPesquisarAluno {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grupo grupo = new Grupo();
	private Aluno aluno1 = new Aluno();
	private Aluno aluno2 = new Aluno();
	private Aluno aluno3 = new Aluno();
	private Aluno aluno4 = new Aluno();
	private String button;
	private String idTurma;
	private String idAlunos;

	@Override
	public void validate() {

	}

	@Override
	public String execute() {
		grupo.setAlunos(new ArrayList<Aluno>());

		if ("Excluir".equals(getButton()))
			return this.Delete();

		if (grupo.getIdGrupo() == null || grupo.getIdGrupo().equals(0)) {
			return this.Insert();
		} else {
			return this.Update();

		}
	}

	public String adicionarGrupo() {

		if (idTurma == null || idTurma.isEmpty() || idAlunos == null || idAlunos.isEmpty())
			return ERROR;

		grupo.setAlunos(new ArrayList<Aluno>());
		grupo.setTurma(new Turma());
		grupo.getTurma().setId(Integer.parseInt(idTurma));

		String[] aux = idAlunos.split(":");
		aux = aux[1].split(",");

		for (int cont = 0; cont < aux.length; cont++) {

			Aluno aluno;

			aluno = pesqAluno.pesquisar(aux[cont]);

			if (aluno == null)
				return ERROR;

			grupo.getAlunos().add(aluno);

		}

		if (grupo.getAlunos().size() > 4 || grupo.getAlunos().size() < 2) {
			addActionError("Quantidade de alunos deve ser superior a 2 e inferior a 4");
			return SUCCESS;
		}

		if (mantGrupo.inserir(grupo)) {
			addActionMessage("Grupo modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}

		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	private String Update() {
		if (mantGrupo.modificar(grupo)) {
			addActionMessage("Grupo modificada com sucesso!");
			this.Clean();
			return SUCCESS;
		}

		addActionError("N�o foi poss�vel realizar a modifica��o");
		return ERROR;
	}

	private String Insert() {
		if (mantGrupo.inserir(grupo)) {
			addActionMessage("Grupo inserida com sucesso!");
			this.Clean();
			return SUCCESS;
		}

		addActionError("N�o foi poss�vel realizar a inser��o");
		return ERROR;
	}

	private String Delete() {
		if (mantGrupo.remover(grupo)) {
			addActionMessage("Grupo removida com sucesso!");
			this.Clean();
			return SUCCESS;
		}

		addActionError("N�o foi poss�vel realizar a remo��o");
		return ERROR;
	}

	private void Clean() {
		this.button = "";
		this.grupo.setIdGrupo(0);
		// this.grupo.getAtividade().setIdAtividade(0);
		this.grupo.getTurma().setId(0);
		this.grupo.getAlunos().clear();
		this.aluno1.setId(0);
		this.aluno2.setId(0);
		this.aluno3.setId(0);
		this.aluno4.setId(0);
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Aluno getAluno1() {
		return aluno1;
	}

	public void setAluno1(Aluno aluno1) {
		this.aluno1 = aluno1;
	}

	public Aluno getAluno2() {
		return aluno2;
	}

	public void setAluno2(Aluno aluno2) {
		this.aluno2 = aluno2;
	}

	public Aluno getAluno3() {
		return aluno3;
	}

	public void setAluno3(Aluno aluno3) {
		this.aluno3 = aluno3;
	}

	public Aluno getAluno4() {
		return aluno4;
	}

	public void setAluno4(Aluno aluno4) {
		this.aluno4 = aluno4;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	@Override
	public Grupo getModel() {
		if (grupo.getTurma().equals(null))
			grupo.setTurma(new Turma());

		// if (grupo.getAtividade().equals(null))
		// grupo.setAtividade(new Atividade());

		return grupo;
	}

	public String getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(String idTurma) {
		this.idTurma = idTurma;
	}

	public String getIdAlunos() {
		return idAlunos;
	}

	public void setIdAlunos(String idAlunos) {
		this.idAlunos = idAlunos;
	}

}
