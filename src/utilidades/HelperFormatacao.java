package utilidades;

import java.util.Collections;

public class HelperFormatacao {
	
	//A utilidade de ter algo assim é que posso mudar o multiplicador sem mudar em tudo
	public static String criaEspacos(int numeroEspacos) {
		//Collections cria uma coleção de "numeroTabs" "\t", e String.join junta essa coleção com a string vazia no meio
		return String.join("", Collections.nCopies(numeroEspacos * 2, " "));		
	}

}
