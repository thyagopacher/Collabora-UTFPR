package modelObjects;

public class Conteudo {
	
	private Ementa ementa;
	private String nome;
	private String descricao;
	private Integer idConteudo;
	
	public Integer getIdConteudo() {
		return idConteudo;
	}
	public void setIdConteudo(int idConteudo) {
		this.idConteudo = idConteudo;
	}
	public Ementa getEmenta() {
		return ementa;
	}
	public void setEmenta(Ementa ementa) {
		this.ementa = ementa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
