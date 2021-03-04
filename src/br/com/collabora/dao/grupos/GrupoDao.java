package br.com.collabora.dao.grupos;

import java.util.Optional;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Aluno;
import modelObjects.Grupo;

@Local
public interface GrupoDao extends DaoGenerico<Grupo> {

	Optional<Grupo> obterGrupoAluno(Aluno aluno);

}
