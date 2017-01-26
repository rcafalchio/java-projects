package com.leilaopecuario.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_lance")
public class Lance {

	@EmbeddedId
	private ChaveLance chaveLance;

	@Column(name = "lance")
	private Double lance;

	@Column(name = "data_lance")
	private Date dataLance;

	public ChaveLance getChaveLance() {
		return chaveLance;
	}

	public void setChaveLance(ChaveLance chaveLance) {
		this.chaveLance = chaveLance;
	}

	public Double getLance() {
		return lance;
	}

	public void setLance(Double lance) {
		this.lance = lance;
	}

	public Date getDataLance() {
		return dataLance;
	}

	public void setDataLance(Date dataLance) {
		this.dataLance = dataLance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chaveLance == null) ? 0 : chaveLance.hashCode());
		result = prime * result + ((dataLance == null) ? 0 : dataLance.hashCode());
		result = prime * result + ((lance == null) ? 0 : lance.hashCode());
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
		Lance other = (Lance) obj;
		if (chaveLance == null) {
			if (other.chaveLance != null)
				return false;
		} else if (!chaveLance.equals(other.chaveLance))
			return false;
		if (dataLance == null) {
			if (other.dataLance != null)
				return false;
		} else if (!dataLance.equals(other.dataLance))
			return false;
		if (lance == null) {
			if (other.lance != null)
				return false;
		} else if (!lance.equals(other.lance))
			return false;
		return true;
	}

}
