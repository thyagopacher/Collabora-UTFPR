package modelObjects;

import java.util.Date;
import java.util.List;

public class Atividade implements ObjetoModelo {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Date inicio;
	private Date fim;
	private int idAtividade;
	private List<Questao> questoes;

	public Atividade() {
	}

	public Atividade(String nome, Date inicio, int idAtividade) {
		this(nome, inicio, null, idAtividade);
	}

	public Atividade(String nome, Date inicio, Date fim, int idAtividade) {
		this.nome = nome;
		this.inicio = inicio;
		this.fim = fim;
		this.idAtividade = idAtividade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Integer getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(int idAtividade) {
		this.idAtividade = idAtividade;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	@Override
	public String getId() {
		return String.valueOf(getIdAtividade());
	}

}
