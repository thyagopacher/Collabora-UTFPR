package modelObjects;

public class Professor extends Usuario {

	private static final long serialVersionUID = 1L;

	private String registro;

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	private int id;

	public String getId() {
		return String.valueOf(id);
	}

	public void setId(int id) {
		this.id = id;
	}

}
