package br.com.tecway.gerenciadorloja.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabela_devolucao")
public class DevolucaoEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	private UsuarioEntity vendedor;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<ProdutoEntity> produtos;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_venda")
	private Date dataVenda;

	@Column(name = "valor_bruto")
	private Double valorBruto;

	@Column(name = "percentual_desconto")
	private Double percentualDesconto;

	@Column(name = "valor_liquido")
	private Double valorLiquido;

	@Column(name = "tipo_pagamento")
	private Integer tipoPagamento;

	@Override
	public String toString() {
		return "br.com.tecway.gerenciadorloja.entity.VendaEntity[ codigo =" + this.codigo + " ]";
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
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

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public List<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	public Integer getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(Integer tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
}
