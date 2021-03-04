package br.com.collabora.dao.alunos;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Aluno;
import modelObjects.Turma;

@Local
public interface AlunoDao extends DaoGenerico<Aluno> {

	List<Aluno> listarAlunosDeTurma(Turma turma);

	Optional<Aluno> obter(Aluno aluno);

}
