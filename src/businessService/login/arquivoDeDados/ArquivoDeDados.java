package businessService.login.arquivoDeDados;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ArquivoDeDados implements IArqDados{

	@Override
	public File Copiar(File file, String fileName, String destPath) {
		File destFile;
		
   	 	try {
   	 		
   	 		destFile  = new File(destPath, fileName);
			FileUtils.copyFile(file, destFile);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
   	 	
   	 	
		return destFile;
	}

	

}
