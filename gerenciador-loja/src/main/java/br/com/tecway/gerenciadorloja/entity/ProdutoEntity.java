package br.com.tecway.gerenciadorloja.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity Produto
 * 
 * @author Ricardo Cafalchios
 * @since 13/08/2013
 */
@Entity
@Table(name = "tabela_produto")
@NamedQueries({
		@NamedQuery(name = "ProdutoEntity.findAll", query = "SELECT a FROM ProdutoEntity a " + " order by a.nome"),
		@NamedQuery(name = "ProdutoEntity.buscarProdutosPeloCodigoBarras", query = "SELECT a FROM ProdutoEntity a "
				+ " WHERE a.codigoBarras = :codigoBarras" + " ORDER BY a.nome"),
		@NamedQuery(name = "ProdutoEntity.buscarProdutosDisponiveisPeloCodigoBarras", query = "SELECT p FROM ProdutoEntity p "
				+ " WHERE p.codigoBarras = :codigoBarras" + " ORDER BY p.nome "),
		@NamedQuery(name = "ProdutoEntity.buscarProdutosPelaVenda", query = "SELECT p FROM ProdutoEntity p INNER JOIN p.vendas v "
				+ " WHERE v.codigo = :codigoVenda " + " ORDER BY p.nome ") })
public class ProdutoEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2637079523386953345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;
	private String nome;
	private String descricao;
	private Double preco;

	@OneToMany(fetch = FetchType.LAZY)
	private List<EstoqueEntity> estoques;

	@OneToMany(fetch = FetchType.LAZY)
	private List<EstoqueCasaEntity> estoquesCasa;

	@ManyToOne(fetch = FetchType.LAZY)
	private MarcaProdutoEntity marca;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<VendaEntity> vendas;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<VendaEntity> trocas;

	@Column(name = "codigo_barras")
	private Long codigoBarras;

	@Column(name = "codigo_tipo_produto")
	private Integer codigoTipoProduto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<EstoqueEntity> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<EstoqueEntity> estoques) {
		this.estoques = estoques;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public MarcaProdutoEntity getMarca() {
		return marca;
	}

	public void setMarca(MarcaProdutoEntity marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return nome;
	}

	public Integer getCodigoTipoProduto() {
		return codigoTipoProduto;
	}

	public void setCodigoTipoProduto(Integer codigoTipoProduto) {
		this.codigoTipoProduto = codigoTipoProduto;
	}

	public Long getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public List<EstoqueCasaEntity> getEstoquesCasa() {
		return estoquesCasa;
	}

	public void setEstoquesCasa(List<EstoqueCasaEntity> estoquesCasa) {
		this.estoquesCasa = estoquesCasa;
	}

	public void setVendas(List<VendaEntity> vendas) {
		this.vendas = vendas;
	}

	public List<VendaEntity> getVendas() {
		return vendas;
	}

	public List<VendaEntity> getTrocas() {
		return trocas;
	}

	public void setTrocas(List<VendaEntity> trocas) {
		this.trocas = trocas;
	}

}
