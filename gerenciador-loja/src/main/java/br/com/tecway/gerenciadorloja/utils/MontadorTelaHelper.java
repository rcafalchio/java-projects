package br.com.tecway.gerenciadorloja.utils;

import br.com.tecway.gerenciadorloja.exception.TelaException;
import javafx.scene.Node;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 06/12/2013
 */
public abstract class MontadorTelaHelper {

	protected Node nodeRaiz;

	public MontadorTelaHelper(Node nodeRaiz) {
		this.nodeRaiz = nodeRaiz;
	}

	public abstract Node montarTela() throws TelaException;

}
