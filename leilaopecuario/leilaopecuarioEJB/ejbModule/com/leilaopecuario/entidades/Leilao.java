package com.leilaopecuario.entidades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cadastro_leilao")
public class Leilao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_leilao")
	private Integer codigo;
	private String titulo;
	private String descricao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_hora_final")
	private Date dataEHoraFinal;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	public Double getLanceInicial() {
		return lanceInicial;
	}

	public void setLanceInicial(Double lanceInicial) {
		this.lanceInicial = lanceInicial;
	}

	@Column(name = "lance_inicial")
	private Double lanceInicial;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "leilao")
	private InformacoesLanceLeilao informacoesLanceLeilao;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "leilao")
	private Animal animal;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "leilao")
	private InformacoesFotos caminhoFotos;

	@ManyToOne
	private Usuario usuario;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataEHoraFinal() {
		return dataEHoraFinal;
	}

	public void setDataEHoraFinal(Date dataEHoraFinal) {
		this.dataEHoraFinal = dataEHoraFinal;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public InformacoesLanceLeilao getInformacoesLanceLeilao() {
		return informacoesLanceLeilao;
	}

	public void setInformacoesLanceLeilao(InformacoesLanceLeilao informacoesLanceLeilao) {
		this.informacoesLanceLeilao = informacoesLanceLeilao;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public InformacoesFotos getCaminhoFotos() {
		return caminhoFotos;
	}

	public void setCaminhoFotos(InformacoesFotos caminhoFotos) {
		this.caminhoFotos = caminhoFotos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animal == null) ? 0 : animal.hashCode());
		result = prime * result + ((caminhoFotos == null) ? 0 : caminhoFotos.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataEHoraFinal == null) ? 0 : dataEHoraFinal.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((informacoesLanceLeilao == null) ? 0 : informacoesLanceLeilao.hashCode());
		result = prime * result + ((lanceInicial == null) ? 0 : lanceInicial.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Leilao other = (Leilao) obj;
		if (animal == null) {
			if (other.animal != null)
				return false;
		} else if (!animal.equals(other.animal))
			return false;
		if (caminhoFotos == null) {
			if (other.caminhoFotos != null)
				return false;
		} else if (!caminhoFotos.equals(other.caminhoFotos))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataEHoraFinal == null) {
			if (other.dataEHoraFinal != null)
				return false;
		} else if (!dataEHoraFinal.equals(other.dataEHoraFinal))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (informacoesLanceLeilao == null) {
			if (other.informacoesLanceLeilao != null)
				return false;
		} else if (!informacoesLanceLeilao.equals(other.informacoesLanceLeilao))
			return false;
		if (lanceInicial == null) {
			if (other.lanceInicial != null)
				return false;
		} else if (!lanceInicial.equals(other.lanceInicial))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
