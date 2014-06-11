package br.com.tecway.gerenciadorloja.common;

import java.util.ArrayList;
import java.util.List;

import br.com.tecway.gerenciadorloja.constants.TipoPagamentoEnum;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;

public class FechamentoVendaVO {

	private List<ProdutoVendaVO> listaProdutos = new ArrayList<ProdutoVendaVO>();
	private UsuarioEntity vendedor;
	private Double percentualDesconto;
	private Double valorBruto;
	private Double valorLiquido;
	private Double valorPago;
	private TipoPagamentoEnum tipoPagamentoEnum;

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public List<ProdutoVendaVO> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ProdutoVendaVO> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public UsuarioEntity getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioEntity vendedor) {
		this.vendedor = vendedor;
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

	public TipoPagamentoEnum getTipoPagamentoEnum() {
		return tipoPagamentoEnum;
	}

	public void setTipoPagamentoEnum(TipoPagamentoEnum tipoPagamentoEnum) {
		this.tipoPagamentoEnum = tipoPagamentoEnum;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

}
