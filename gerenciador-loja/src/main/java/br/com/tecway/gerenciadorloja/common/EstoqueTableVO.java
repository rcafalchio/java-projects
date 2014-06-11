package br.com.tecway.gerenciadorloja.common;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 02/12/2013
 */
public class EstoqueTableVO {

	private ProdutoVO produtoTableVO;
	private Integer quantidade;
	private Long codigoBarras;

	public ProdutoVO getProdutoTableVO() {
		return produtoTableVO;
	}

	public void setProdutoTableVO(ProdutoVO produtoTableVO) {
		this.produtoTableVO = produtoTableVO;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public void somar() {
		this.quantidade++;
	}

	@Override
	public boolean equals(Object obj) {

		boolean retorno = false;

		if (obj != null && obj instanceof EstoqueTableVO) {

			final EstoqueTableVO estoqueTableVO = (EstoqueTableVO) obj;
			retorno = estoqueTableVO.getCodigoBarras().equals(this.codigoBarras);

		}

		return retorno;

	}

}
