package br.com.tecway.gerenciadorloja.common;

public class ProdutoVO {

	private Integer codigo;
	private String nome;
	private String descricao;
	private MarcaVO marca;
	private String preco;
	private Long codigoBarras;
	private Integer codigoTipoProduto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public Long getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public MarcaVO getMarca() {
		return marca;
	}

	public void setMarca(MarcaVO marca) {
		this.marca = marca;
	}

	public Integer getCodigoTipoProduto() {
		return codigoTipoProduto;
	}

	public void setCodigoTipoProduto(Integer codigoTipoProduto) {
		this.codigoTipoProduto = codigoTipoProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
