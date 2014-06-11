package br.com.tecway.gerenciadorloja.business;

import br.com.tecway.gerenciadorloja.common.DadosArquivoVO;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorArquivo {

	/**
	 * 
	 * @param dadosArquivoVO
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void gerarArquivo(final DadosArquivoVO dadosArquivoVO) throws BusinessException, DAOException;
	
}
