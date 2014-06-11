package br.com.tecway.gerenciadorloja.utils;

import javafx.scene.Node;
import br.com.tecway.gerenciadorloja.constants.TipoEstoqueEnum;

public class EstoqueCasaTelaHelper extends EstoqueTelaHelper {

	public EstoqueCasaTelaHelper(Node nodeRaiz, Boolean estoqueCasa) {
		super(nodeRaiz, TipoEstoqueEnum.CASA);
	}

}
