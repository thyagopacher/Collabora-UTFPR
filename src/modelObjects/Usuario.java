package modelObjects;

import org.apache.commons.lang.NotImplementedException;

public class Usuario implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String getId() {
		throw new NotImplementedException();
	}

}
