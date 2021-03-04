package br.com.collabora.dao.professores;

import javax.ejb.Local;

import br.com.collabora.dao.DaoGenerico;
import modelObjects.Professor;

@Local
public interface ProfessorDao extends DaoGenerico<Professor> {

}
