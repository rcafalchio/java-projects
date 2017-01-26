package br.com.tecway.gerenciadorloja.business;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorEstoque {

	/**
	 * Atualiza a unidade no estoque do produto
	 * 
	 * @param EstoqueTableVO
	 *            estoqueTableVO
	 * @throws BusinessException
	 *             exception de negócio
	 * @throws DAOException
	 */
	void adicionarEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException, DAOException;

	/**
	 * Remove a unidade no estoque do produto
	 * 
	 * @param EstoqueTableVO
	 *            estoqueTableVO
	 * @throws BusinessException
	 *             exception de negócio
	 * @throws DAOException
	 */
	void removerEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException, DAOException;

	/**
	 * Verifica se os produtos solicitados existem no estoque
	 * 
	 * @param recuperarValorIntegerTextField
	 * @param codigoBarras
	 * @throws BusinessException
	 * @throws DAOException
	 */
	ProdutoEntity verificarExistenciaProdutos(Integer recuperarValorIntegerTextField, Long codigoBarras)
			throws BusinessException, DAOException;

}
