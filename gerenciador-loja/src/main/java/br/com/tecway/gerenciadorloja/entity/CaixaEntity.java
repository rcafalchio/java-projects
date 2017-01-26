package br.com.tecway.gerenciadorloja.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tabela_caixa")
@NamedQueries({
		@NamedQuery(name = "CaixaEntity.buscarCaixas", query = "SELECT a FROM CaixaEntity a "
				+ " WHERE a.data >= :dataInicial " + " AND a.data <= :dataFinal"),
		@NamedQuery(name = "CaixaEntity.buscarUltimoCaixa", query = "SELECT a FROM CaixaEntity a  WHERE a "
				+ " in ( SELECT MAX(b.data) FROM CaixaEntity b )") })
public class CaixaEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(name = "valor_caixa")
	private Double valorDinheiro;

	@Column(name = "valor_cartao_credito")
	private Double valorCartaoCredito;

	@Column(name = "valor_cartao_debito")
	private Double valorCartaoDebito;

	@Override
	public String toString() {
		return "br.com.tecway.gerenciadorloja.entity.CaixaEntity[ valorDinheiro =" + this.valorDinheiro + " ]";
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValorDinheiro() {
		return valorDinheiro;
	}

	public void setValorDinheiro(Double valorDinheiro) {
		this.valorDinheiro = valorDinheiro;
	}

	public Double getValorCartaoCredito() {
		return valorCartaoCredito;
	}

	public void setValorCartaoCredito(Double valorCartaoCredito) {
		this.valorCartaoCredito = valorCartaoCredito;
	}

	public Double getValorCartaoDebito() {
		return valorCartaoDebito;
	}

	public void setValorCartaoDebito(Double valorCartaoDebito) {
		this.valorCartaoDebito = valorCartaoDebito;
	}
}
