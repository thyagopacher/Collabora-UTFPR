package br.com.collabora.facades;

public interface ContainerControlador<T extends Controlador> {
	T getControlador();
}
