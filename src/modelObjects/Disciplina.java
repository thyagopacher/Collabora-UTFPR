package modelObjects;

public class Disciplina implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private int idDisicplina;
	private String nome, codigo;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getId() {
		return String.valueOf(getIdDisicplina());
	}

	public Integer getIdDisicplina() {
		return idDisicplina;
	}

	public void setIdDisicplina(int idDisicplina) {
		this.idDisicplina = idDisicplina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
