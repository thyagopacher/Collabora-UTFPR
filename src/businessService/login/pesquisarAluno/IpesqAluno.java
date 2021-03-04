package businessService.login.pesquisarAluno;

import java.sql.ResultSet;
import java.util.List;

import modelObjects.Aluno;

public interface IpesqAluno {
	
	public Aluno pesquisar(String registro);
	
	public Aluno pesquisarPorId(String id);
	
	public Aluno pesquisar(Aluno aluno);
	
	public Aluno transformar(ResultSet rs);
	
	public List<Aluno> pesquisarNome(String Nome);
	
	public List<Aluno> transformarConjunto(ResultSet rs);

}
