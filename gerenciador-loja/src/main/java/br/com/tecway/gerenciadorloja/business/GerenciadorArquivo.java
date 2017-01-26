package br.com.tecway.gerenciadorloja.business;

import br.com.tecway.gerenciadorloja.common.DadosArquivoVO;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.exception.ExportadorDadosException;
import br.com.tecway.gerenciadorloja.fx.components.ExportadorDados;

public class GerenciadorArquivo implements IGerenciadorArquivo {

	@Override
	public void gerarArquivo(DadosArquivoVO dadosArquivoVO) throws BusinessException, DAOException {
		try {
			if (dadosArquivoVO.getCaminhoArquivo() == null) {
				throw new BusinessException("É necessário informar o caminho que o arquivo será gerado!");
			}
			ExportadorDados.exportarDados(dadosArquivoVO);
		} catch (ExportadorDadosException e) {
			throw new BusinessException("Ocorreu um erro ao gerar o arquivo!");
		}
	}

}
