package com.leilaopecuario.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "dados_fotos_leilao")
public class InformacoesFotos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = { @Parameter(name = "property", value = "leilao") })
	@Column(name = "codigo_leilao")
	private Integer codigoLeilao;

	private String caminho;

	@Column(name = "foto_principal")
	private String fotoPrincipal;

	@OneToOne
	private Leilao leilao;

	public Integer getCodigoLeilao() {
		return codigoLeilao;
	}

	public void setCodigoLeilao(Integer codigoLeilao) {
		this.codigoLeilao = codigoLeilao;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getFotoPrincipal() {
		return fotoPrincipal;
	}

	public void setFotoPrincipal(String fotoPrincipal) {
		this.fotoPrincipal = fotoPrincipal;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caminho == null) ? 0 : caminho.hashCode());
		result = prime * result + ((codigoLeilao == null) ? 0 : codigoLeilao.hashCode());
		result = prime * result + ((fotoPrincipal == null) ? 0 : fotoPrincipal.hashCode());
		result = prime * result + ((leilao == null) ? 0 : leilao.hashCode());
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
		InformacoesFotos other = (InformacoesFotos) obj;
		if (caminho == null) {
			if (other.caminho != null)
				return false;
		} else if (!caminho.equals(other.caminho))
			return false;
		if (codigoLeilao == null) {
			if (other.codigoLeilao != null)
				return false;
		} else if (!codigoLeilao.equals(other.codigoLeilao))
			return false;
		if (fotoPrincipal == null) {
			if (other.fotoPrincipal != null)
				return false;
		} else if (!fotoPrincipal.equals(other.fotoPrincipal))
			return false;
		if (leilao == null) {
			if (other.leilao != null)
				return false;
		} else if (!leilao.equals(other.leilao))
			return false;
		return true;
	}

}