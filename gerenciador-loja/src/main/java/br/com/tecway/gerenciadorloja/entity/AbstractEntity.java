package br.com.tecway.gerenciadorloja.entity;

import java.io.Serializable;

import br.com.tecway.gerenciadorloja.utils.EntityAnalyzer;

/**
 * Classe abstrata das entidades da aplicação.
 * 
 * @author Fabio C. Krambek
 */
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(final Object other) {
		return EntityAnalyzer.equalsAnalyzer(this, other);
	}

	@Override
	public int hashCode() {
		return EntityAnalyzer.hashCodeAnalyzer(this);
	}

	@Override
	public abstract String toString();

}