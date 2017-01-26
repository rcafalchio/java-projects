package br.com.tecway.gerenciadorloja.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tabela_estoque_casa")
@NamedQueries({ @NamedQuery(name = "EstoqueCasaEntity.buscarUnidadesDisponiveis", query = "SELECT e FROM EstoqueCasaEntity e INNER JOIN e.produto p "
		+ "WHERE p.codigo = :codigo") })
public class EstoqueCasaEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProdutoEntity produto;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "br.com.tecway.gerenciadorloja.entity.EstoqueCasaEntity[ codigo =" + this.codigo + " ]";
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
