package modelObjects;



public class Ementa {
	
	private Integer idEmenta;
	private Disciplina disciplina;
	private String nome;
	
	public Integer getIdEmenta() {
		return idEmenta;
	}
	public void setIdEmenta(int idEmenta) {
		this.idEmenta = idEmenta;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}
