package com.leilaopecuario.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "cadastro_animal")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = { @Parameter(name = "property", value = "leilao") })
	@Column(name = "codigo_leilao")
	private Integer codigoLeilao;

	@PrimaryKeyJoinColumn
	@OneToOne
	private Leilao leilao;

	private String raca;
	@Column(name = "idade_em_meses")
	private Integer idadeMeses;
	@Column(name = "localidade_de_criacao")
	private String localidadeCriacao;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "animais")
	private List<Vacina> vacinas = new ArrayList<Vacina>();

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public Integer getIdadeMeses() {
		return idadeMeses;
	}

	public void setIdadeMeses(Integer idadeMeses) {
		this.idadeMeses = idadeMeses;
	}

	public String getLocalidadeCriacao() {
		return localidadeCriacao;
	}

	public void setLocalidadeCriacao(String localidadeCriacao) {
		this.localidadeCriacao = localidadeCriacao;
	}

	public Integer getCodigoLeilao() {
		return codigoLeilao;
	}

	public void setCodigoLeilao(Integer codigoLeilao) {
		this.codigoLeilao = codigoLeilao;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoLeilao == null) ? 0 : codigoLeilao.hashCode());
		result = prime * result + ((idadeMeses == null) ? 0 : idadeMeses.hashCode());
		result = prime * result + ((leilao == null) ? 0 : leilao.hashCode());
		result = prime * result + ((localidadeCriacao == null) ? 0 : localidadeCriacao.hashCode());
		result = prime * result + ((raca == null) ? 0 : raca.hashCode());
		result = prime * result + ((vacinas == null) ? 0 : vacinas.hashCode());
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
		Animal other = (Animal) obj;
		if (codigoLeilao == null) {
			if (other.codigoLeilao != null)
				return false;
		} else if (!codigoLeilao.equals(other.codigoLeilao))
			return false;
		if (idadeMeses == null) {
			if (other.idadeMeses != null)
				return false;
		} else if (!idadeMeses.equals(other.idadeMeses))
			return false;
		if (leilao == null) {
			if (other.leilao != null)
				return false;
		} else if (!leilao.equals(other.leilao))
			return false;
		if (localidadeCriacao == null) {
			if (other.localidadeCriacao != null)
				return false;
		} else if (!localidadeCriacao.equals(other.localidadeCriacao))
			return false;
		if (raca == null) {
			if (other.raca != null)
				return false;
		} else if (!raca.equals(other.raca))
			return false;
		if (vacinas == null) {
			if (other.vacinas != null)
				return false;
		} else if (!vacinas.equals(other.vacinas))
			return false;
		return true;
	}

}
