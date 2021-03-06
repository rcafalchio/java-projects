package com.leilaopecuario.vo;

import java.util.ArrayList;
import java.util.List;

public class ContatoVO {

	private String email;
	private String site;
	private String msn;
	private String skype;
	
	private List<TelefoneVO> telefones = null;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<TelefoneVO> getTelefones() {
		if (telefones == null) {
			telefones = new ArrayList<TelefoneVO>();
		}
		return telefones;
	}

	public void setTelefones(List<TelefoneVO> telefones) {
		this.telefones = telefones;
	}

}
