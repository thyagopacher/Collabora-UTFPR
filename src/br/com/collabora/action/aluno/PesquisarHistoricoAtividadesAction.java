package br.com.collabora.action.aluno;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import br.com.collabora.dao.atividades.AtividadeDao;
import br.com.collabora.facades.FacadeEJB;
import br.com.collabora.facades.ManipuladorFacades;
import br.com.collabora.modelo.dtos.atividades.AtividadeDTO;
import br.com.collabora.sessoes.alunos.ControladorSessaoAluno;

public class PesquisarHistoricoAtividadesAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private List<AtividadeDTO> records = new ArrayList<>();

	@Override
	public String execute() throws Exception {
		records.clear();
		records.addAll(getAtividadeDao()
				.listarDTOsAtividadesFinalizadasDoGrupo(getControladorSessao().getGrupo().orElse(null)));

		return SUCCESS;
	}

	public List<AtividadeDTO> getRecords() {
		return records;
	}

	public void setRecords(List<AtividadeDTO> records) {
		this.records = records;
	}

	private ControladorSessaoAluno getControladorSessao() {
		return ManipuladorFacades.getControlador(ControladorSessaoAluno.class);
	}

	private AtividadeDao getAtividadeDao() {
		return FacadeEJB.obterPelaClasseDoBean(AtividadeDao.class);
	}

}
