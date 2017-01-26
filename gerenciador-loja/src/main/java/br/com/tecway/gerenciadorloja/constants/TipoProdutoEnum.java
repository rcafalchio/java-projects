package br.com.tecway.gerenciadorloja.constants;

public enum TipoProdutoEnum {

	CELULARES("Celulares", 1), COMPUTADORES("Computadores", 2), ACESSORIOS("Acessórios", 3), VIDEOGAMES("Videogames", 4), OUTROS(
			"Outros", 5);

	private String nome;
	private Integer codigo;

	private TipoProdutoEnum(String nome, Integer codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * Recupera o tipo de produto
	 * 
	 * @param codigo
	 * @return TipoProdutoEnum
	 */
	public static TipoProdutoEnum getTipoProdutoEnumPorCodigo(Integer codigo) {

		TipoProdutoEnum tipoProdutoEnum = null;

		for (TipoProdutoEnum tipoProdutoEnum2 : TipoProdutoEnum.values()) {

			if (tipoProdutoEnum2.getCodigo().equals(codigo)) {
				tipoProdutoEnum = tipoProdutoEnum2;
			}

		}

		return tipoProdutoEnum;

	}

}
