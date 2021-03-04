package modelObjects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Aluno extends Usuario {

	private static final long serialVersionUID = 1L;

	private String registro;
	private Integer id;

	public Aluno() {
	}

	public Aluno(String nome, String registro, Integer id) {
		this.id = id;
		this.registro = registro;
		this.setNome(nome);
	}

	public Aluno(String registro, Integer id) {
		this.registro = registro;
		this.id = id;
	}

	@Override
	public String getId() {
		return String.valueOf(id);
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Aluno) {
			return this.getId().equals(((Aluno) obj).getId());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

}
