package br.com.tecway.gerenciadorloja.utils;

import javafx.scene.Node;
import br.com.tecway.gerenciadorloja.constants.TipoEstoqueEnum;

public class EstoqueLojaTelaHelper extends EstoqueTelaHelper {

	public EstoqueLojaTelaHelper(Node nodeRaiz) {
		super(nodeRaiz, TipoEstoqueEnum.LOJA);
	}

}
