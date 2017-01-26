package br.com.tecway.gerenciadorloja.common;

import br.com.tecway.gerenciadorloja.constants.TipoArquivoEnum;

public class DadosArquivoVO {

	private TipoArquivoEnum tipoArquivoEnum;
	private String[][] dados;
	private String[] titulosColunas;
	private String titulo;
	private String caminhoArquivo;

	public TipoArquivoEnum getTipoArquivoEnum() {
		return tipoArquivoEnum;
	}

	public void setTipoArquivoEnum(TipoArquivoEnum tipoArquivoEnum) {
		this.tipoArquivoEnum = tipoArquivoEnum;
	}

	public String[][] getDados() {
		return dados;
	}

	public void setDados(String[][] dados) {
		this.dados = dados;
	}

	public String[] getTitulosColunas() {
		return titulosColunas;
	}

	public void setTitulosColunas(String[] titulosColunas) {
		this.titulosColunas = titulosColunas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

}
