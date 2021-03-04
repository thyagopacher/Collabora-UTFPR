package modelObjects;

public class Gerente extends Usuario {

	private static final long serialVersionUID = 1L;

	private Integer id;

	public String getId() {
		return String.valueOf(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
