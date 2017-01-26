package br.com.tecway.gerenciadorloja.entity;

import java.io.Serializable;

import br.com.tecway.gerenciadorloja.utils.EntityAnalyzer;

/**
 * Classe abstrata das pks das entidades da aplicação.
 * 
 * @author F0110415 - Henrique Guedes
 * @since 04/01/2013 14:13:01
 */
public abstract class AbstractEntityPK implements Serializable {

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