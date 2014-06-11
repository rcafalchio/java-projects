package br.com.tecway.gerenciadorloja.common;

import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.constants.TipoPagamentoEnum;

public class VendaVO {

	private Long codigo;
	private UsuarioVO vendedor;
	private Date dataVenda;
	private Double valorBruto;
	private Double percentualDesconto;
	private Double valorLiquido;
	private TipoPagamentoEnum tipoPagamentoEnum;
	private List<ProdutoVO> produtos;

	public UsuarioVO getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioVO vendedor) {
		this.vendedor = vendedor;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public TipoPagamentoEnum getTipoPagamentoEnum() {
		return tipoPagamentoEnum;
	}

	public void setTipoPagamentoEnum(TipoPagamentoEnum tipoPagamentoEnum) {
		this.tipoPagamentoEnum = tipoPagamentoEnum;
	}

	public List<ProdutoVO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoVO> produtos) {
		this.produtos = produtos;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
