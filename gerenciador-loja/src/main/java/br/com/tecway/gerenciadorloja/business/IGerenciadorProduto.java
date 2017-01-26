package br.com.tecway.gerenciadorloja.business;

import java.util.List;

import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public interface IGerenciadorProduto {

	/**
	 * Atualiza o produto
	 * 
	 * @param produtoEntity
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void atualizarProduto(ProdutoEntity produtoEntity) throws BusinessException, DAOException;

	/**
	 * Cadastra o produto
	 * 
	 * @param produtoEntity
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void cadastrarProduto(ProdutoEntity produtoEntity) throws BusinessException, DAOException;

	/**
	 * Excluí um produto selecionado
	 * 
	 * @param codigo
	 * @throws BusinessException
	 * @throws DAOException
	 */
	void excluirProduto(Integer codigo) throws BusinessException, DAOException;

	/**
	 * Busca os produtos de acordo com o código da venda
	 * 
	 * @param codigo
	 * @return List<ProdutoVO>
	 * @throws DAOException
	 * @throws ConverterException
	 */
	List<ProdutoVO> buscarProdutosVendidos(Long codigoVenda) throws DAOException, ConverterException;

}
