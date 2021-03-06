package com.leilaopecuario.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChaveLance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "codigo_usuario")
	private Integer codigoUsuario;
	@Column(name = "codigo_leilao")
	private Integer codigoLeilao;

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Integer getCodigoLeilao() {
		return codigoLeilao;
	}

	public void setCodigoLeilao(Integer codigoLeilao) {
		this.codigoLeilao = codigoLeilao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoLeilao == null) ? 0 : codigoLeilao.hashCode());
		result = prime * result + ((codigoUsuario == null) ? 0 : codigoUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChaveLance other = (ChaveLance) obj;
		if (codigoLeilao == null) {
			if (other.codigoLeilao != null)
				return false;
		} else if (!codigoLeilao.equals(other.codigoLeilao))
			return false;
		if (codigoUsuario == null) {
			if (other.codigoUsuario != null)
				return false;
		} else if (!codigoUsuario.equals(other.codigoUsuario))
			return false;
		return true;
	}

}
