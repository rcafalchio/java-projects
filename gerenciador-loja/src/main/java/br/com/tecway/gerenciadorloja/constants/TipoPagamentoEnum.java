package br.com.tecway.gerenciadorloja.constants;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/12/2013
 */
public enum TipoPagamentoEnum {

	DINHEIRO("DINHEIRO", 1), CARTAO_DEBITO("CARTAO DE DÉBITO", 2), CARTAO_CREDITO("CARTAO DE CRÉDITO", 3);
	// CHEQUE("CHEQUE", 4),
	// OUTROS("OUTROS", 5);

	private String nome;
	private Integer codigo;

	private TipoPagamentoEnum(String nome, Integer codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	/**
	 * Recupera o tipo de pagamento
	 * 
	 * @param codigo
	 * @return
	 */
	public static TipoPagamentoEnum getPagamento(Integer codigo) {
		TipoPagamentoEnum retorno = null;

		if (codigo != null) {
			for (TipoPagamentoEnum tipoPagamento : TipoPagamentoEnum.values()) {
				if (tipoPagamento.getCodigo().equals(codigo)) {
					retorno = tipoPagamento;
				}
			}
		}

		return retorno;
	}
}
