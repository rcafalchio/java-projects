package br.com.tecway.gerenciadorloja.common;

public class UsuarioVO {

	private Integer codigo;
	private String nome;

	@Override
	public String toString() {
		if (this.nome == null) {
			return super.toString();
		} else {
			return this.nome;
		}

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
