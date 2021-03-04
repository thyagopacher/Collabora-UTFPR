package businessService.CRUDAprendizagem.manterTurma;

import businessService.CRUDAprendizagem.pesquisarDisciplina.IPesquisarDisciplina;
import businessService.CRUDAprendizagem.pesquisarTurma.IPesquisarTurma;
import businessService.CRUDAprendizagem.psequisarConjuntoAluno.IPesquisarConjuntoAluno;
import businessService.dbConnection.persistencia.IPersistencia;
import businessService.login.pesquisarProfessor.IPesquisarProfessor;
import modelObjects.Aluno;
import modelObjects.Turma;

/*Classe implementa as interfaces:
 * 	
 * Fornecidas:
 * 	- Oferece a interface com o banco de dados (IPersistencia)
 * 	- Servi�os de pesquisa:	
 * 			- Conjunto de alunos 	(IPesquisarConjuntoAluno)
 * 			- Disciplina 			(IPesquisarDisciplina)
 * 			- Professor 			(IPesquisarProfessor)
 * 			- Turma					(IPesquisarTurma)
 * 
 * Requerida:
 * - IMantTurma, cont�m as defini��es do servi�o de crud para turma
 * 
 * */
public class ManterTurma implements IMantTurma, IPersistencia, IPesquisarConjuntoAluno, IPesquisarDisciplina,
		IPesquisarProfessor, IPesquisarTurma {

	private String query;

	/*
	 * INSERIR
	 * 
	 * 
	 * Insere a turma e os alunos da turma passada como referencia na base de
	 * dados.
	 * 
	 * Retorna falso caso: - os parametros passados sejam nulos ou vazios; - se
	 * algum dos alunos referenciados n�o existir - se a disciplina ou professor
	 * n�o existirem; - se j� existir uma turma com o mesmo nome; - se n�o for
	 * poss�vel realizar a inser��o;
	 * 
	 * Retorna verdadeiro: - se a turma e os alunos forem inseridos com sucesso.
	 */
	public boolean inserir(Turma turma) {
		boolean part1 = false;

		if (turma == null || turma.getAluno().isEmpty())
			return false;

		// verifica se todos os alunos passados existem
		if (!pesqConjAlu.pesquisar(turma.getAluno()))
			return false;

		// realiza a verifica��o da existencia da disciplina
		if (pesqDisc.pesquisar(turma.getDisciplina().getIdDisicplina()) == null)
			return false;

		// realiza a verifica��o da existencia do professor
		if (pesqProf.pesquisarPorId(Integer.valueOf(turma.getProfessor().getId())) == null)
			return false;

		// verifica se a turma j� existe
		if (pesqTurma.pesquisar(turma.getCodigo()) != null)
			return false;

		this.query = "insert into turma(codigo, iddisciplina, idprofessor) values " + "('"
				+ turma.getCodigo().toUpperCase() + "'," + turma.getDisciplina().getIdDisicplina() + ", "
				+ turma.getProfessor().getId() + ");";

		part1 = (pers.ExecuteUpdate(query) > 0);

		Turma aux;
		if (part1) {
			aux = pesqTurma.pesquisar(turma.getCodigo());
		} else
			return false;

		turma.setId(Integer.valueOf(aux.getId()));

		return (this.inserirAlunosTurma(turma));

	}

	/*
	 * Inserir Alunos Turma
	 * 
	 * Insere os alunos de uma turma na tabela associativa alunoTurma.
	 * 
	 * Retorna falso caso: - os parametros passados sejam nulos ou vazios; - se
	 * n�o for poss�vel realizar a inser��o;
	 * 
	 * Retorna verdadeiro: - se todas as inser��es ocorreram
	 */
	public boolean inserirAlunosTurma(Turma turma) {

		if (turma.getAluno().isEmpty() || turma == null)
			return false;

		for (Aluno aluno : turma.getAluno()) {

			this.query = "insert into alunoturma (idaluno,idturma) values " + "(" + aluno.getId() + "," + turma.getId()
					+ ");";

			if (pers.ExecuteUpdate(query) < 1)
				return false;
		}

		return true;
	}

	/*
	 * MODIFICAR
	 * 
	 * 
	 * modifica a turma e os alunos da turma passada como referencia na base de
	 * dados.
	 * 
	 * Retorna falso caso: - os parametros passados sejam nulos ou vazios; - se
	 * algum dos alunos referenciados n�o existir - se a disciplina ou professor
	 * n�o existirem; - se j� existir uma turma com o mesmo nome; - se n�o for
	 * poss�vel realizar a modifica��o;
	 * 
	 * Retorna verdadeiro: - se a turma e os alunos forem modificados com
	 * sucesso.
	 */
	public boolean modificar(Turma turma) {
		boolean part1 = false;

		if (turma == null || turma.getAluno().isEmpty())
			return false;

		if (!pesqConjAlu.pesquisar(turma.getAluno()))
			return false;

		if (pesqDisc.pesquisar(turma.getDisciplina().getIdDisicplina()) == null)
			return false;

		if (pesqProf.pesquisarPorId(Integer.valueOf(turma.getProfessor().getId())) == null)
			return false;

		if (pesqTurma.pesquisarPorId(turma.getId()) == null)
			return false;

		this.query = "update turma set codigo = '" + turma.getCodigo().toUpperCase() + "', iddisciplina = "
				+ turma.getDisciplina().getIdDisicplina() + ", idprofessor = " + turma.getProfessor().getId() + " where"
				+ " idturma = " + turma.getId() + ";";

		part1 = pers.ExecuteUpdate(query) > 0;

		return (part1 && this.modificarAlunosTurma(turma));
	}

	/*
	 * Remover
	 * 
	 * Remove uma Turma
	 * 
	 * Retorna falso se n�o foi poss�vel realizar a remo��o em alunoTurma ou se
	 * n�o foi poss�vel remover a turma
	 * 
	 * Retorna verdadeiro se toda referencia em alunoTurma e turma for removida
	 * 
	 */
	public boolean remover(Turma turma) {
		boolean part1 = false;

		if (turma == null)
			return false;

		part1 = this.removerAlunosTurma(turma);

		if (part1)
			this.query = "delete from turma where idturma = " + turma.getId() + ";";
		else
			return false;

		return (pers.ExecuteUpdate(query) > 0);

	}

	/*
	 * Modificar Alunos Turma
	 * 
	 * Modifica os alunos de uma turma na tabela associativa alunoTurma.
	 * 
	 * Retorna falso caso: - os parametros passados sejam nulos; - se n�o for
	 * poss�vel realizar a remo��o dos alunos; - se n�o for poss�vel realizar a
	 * inser��o dos alunos;
	 * 
	 * Retorna verdadeiro: - se todas as inser��es ocorreram
	 */
	public boolean modificarAlunosTurma(Turma turma) {

		if (turma.getAluno() == null || turma == null)
			return false;

		if (this.removerAlunosTurma(turma)) {
			return this.inserirAlunosTurma(turma);
		}

		return false;
	}

	/*
	 * Remover Alunos Turma
	 * 
	 * Remove todos os alunos de uma determinada turma.
	 * 
	 * Retorna falso se a turma passada por referencia � nula ou se n�o foi
	 * poss�vel realizar a remo��o destes alunos da tabela associativa
	 * alunoTurma
	 * 
	 * Retorna verdadeiro se todos os alunos foram excluidos da tabela
	 * 
	 */
	public boolean removerAlunosTurma(Turma turma) {

		if (turma == null)
			return false;

		this.query = "delete from alunoturma where idturma = " + turma.getId() + ";";

		return pers.ExecuteUpdate(query) > 0;
	}

}
