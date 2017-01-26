package br.com.tecway.gerenciadorloja.fx.controller;


public abstract class AbstractSubController<P extends AbstractController> extends AbstractController {

	private P parent;
	
	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParentController(final P parent) {
		this.parent = parent;
	}

	/**
	 * @return the parent
	 */
	public P getParentController() {
		return this.parent;
	}

}
