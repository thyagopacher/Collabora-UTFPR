package businessService.CRUDAprendizagem.manterQuestao;

import java.util.ArrayList;
import java.util.List;

import modelObjects.Conteudo;
import modelObjects.Disciplina;
import modelObjects.Ementa;
import modelObjects.Proposicao;
import modelObjects.Questao;
import modelObjects.Referencia;

public class teste implements IManterQuestao{
	
	public static void main(String[] args) {
		
		Proposicao p1 = new Proposicao();
		p1.setCorreta(false);
		p1.setEnunciado("teste");
		p1.setImagem("teste");
		p1.setNumero("1");
		
		List<Proposicao> lp = new ArrayList<>();
		lp.add(p1);
		
		Questao q = new Questao();
		
		q.setProposicoes(lp);
		
		Referencia r = new Referencia();
		r.setIdReferencia(1);
		
		q.setReferencia(r);
		
		q.setDificuldade("baixa");
		q.setEnunciado("testePropo");
		q.setImagem("testePropo");
		q.setPeso(0.1);
		q.setConteudo(new Conteudo());
		q.getConteudo().setIdConteudo(3);
		q.getConteudo().setEmenta(new Ementa());
		q.getConteudo().getEmenta().setIdEmenta(1);
		q.getConteudo().getEmenta().setDisciplina(new Disciplina());
		q.getConteudo().getEmenta().getDisciplina().setIdDisicplina(1);
		
		System.out.println(mantQuestao.insert(q));
		
	}

}
