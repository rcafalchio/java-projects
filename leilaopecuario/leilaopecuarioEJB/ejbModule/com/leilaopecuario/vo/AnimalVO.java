package com.leilaopecuario.vo;

import java.util.ArrayList;
import java.util.List;

public class AnimalVO {

	private String raca;
	private List<VacinaVO> vacinas;
	private Integer idadeMeses;
	private String localidadeCriacao;

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

	public List<VacinaVO> getVacinas() {
		if (this.vacinas == null) {
			vacinas = new ArrayList<VacinaVO>();
		}
		return vacinas;
	}

	public void setVacinas(List<VacinaVO> vacinas) {
		this.vacinas = vacinas;
	}

}
