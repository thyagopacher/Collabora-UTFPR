package br.com.collabora.dao;

import java.util.List;
import java.util.Optional;

import modelObjects.ObjetoModelo;

public interface DaoGenerico<T extends ObjetoModelo> {

	boolean inserir(T obj);

	boolean modificar(T obj);

	boolean remover(String id);

	Optional<T> obter(String id);

	List<T> listarTodos();

}
