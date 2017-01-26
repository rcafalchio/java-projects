package com.leilaopecuario.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_vacina")
public class Vacina {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_vacina")
	private Integer codigoVacina;

	@Column(name = "sigla_vacina")
	private String sigla;

	@Column(name = "nome_vacina")
	private String nomeVacina;

	@Column(name = "descricao_vacina")
	private String descricaoVacina;

	@ManyToMany
	private List<Animal> animais = new ArrayList<Animal>();

	public Integer getCodigoVacina() {
		return codigoVacina;
	}

	public void setCodigoVacina(Integer codigoVacina) {
		this.codigoVacina = codigoVacina;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}

	public String getDescricaoVacina() {
		return descricaoVacina;
	}

	public void setDescricaoVacina(String descricaoVacina) {
		this.descricaoVacina = descricaoVacina;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animais == null) ? 0 : animais.hashCode());
		result = prime * result + ((codigoVacina == null) ? 0 : codigoVacina.hashCode());
		result = prime * result + ((descricaoVacina == null) ? 0 : descricaoVacina.hashCode());
		result = prime * result + ((nomeVacina == null) ? 0 : nomeVacina.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Vacina other = (Vacina) obj;
		if (animais == null) {
			if (other.animais != null)
				return false;
		} else if (!animais.equals(other.animais))
			return false;
		if (codigoVacina == null) {
			if (other.codigoVacina != null)
				return false;
		} else if (!codigoVacina.equals(other.codigoVacina))
			return false;
		if (descricaoVacina == null) {
			if (other.descricaoVacina != null)
				return false;
		} else if (!descricaoVacina.equals(other.descricaoVacina))
			return false;
		if (nomeVacina == null) {
			if (other.nomeVacina != null)
				return false;
		} else if (!nomeVacina.equals(other.nomeVacina))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
}
