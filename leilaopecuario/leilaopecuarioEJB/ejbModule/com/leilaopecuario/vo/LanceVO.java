package com.leilaopecuario.vo;

public class LanceVO {

	private LeilaoVO leilaoVO;
	private UsuarioVO usuarioVO;
	private Double lance;
	
	public LeilaoVO getLeilaoVO() {
		return leilaoVO;
	}
	public void setLeilaoVO(LeilaoVO leilaoVO) {
		this.leilaoVO = leilaoVO;
	}
	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}
	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}
	public Double getLance() {
		return lance;
	}
	public void setLance(Double lance) {
		this.lance = lance;
	}

}
