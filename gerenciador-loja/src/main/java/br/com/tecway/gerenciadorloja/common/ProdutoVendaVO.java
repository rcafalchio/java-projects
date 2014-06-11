package br.com.tecway.gerenciadorloja.common;

public class ProdutoVendaVO {

	private ProdutoVO produtoVO;
	private Integer quantidade;

	public ProdutoVO getProdutoVO() {
		return produtoVO;
	}

	public void setProdutoVO(ProdutoVO produtoVO) {
		this.produtoVO = produtoVO;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
