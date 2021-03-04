package businessService.login.SalvarDados;

import java.io.File;

public interface ISalDados {

	public String validarEntradaAluno(File file);

	public String validarEntradaProfessor(File file);

	public String validarEntradaGerente(File file);

}
