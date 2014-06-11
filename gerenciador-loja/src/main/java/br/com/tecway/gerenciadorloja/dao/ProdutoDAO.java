package br.com.tecway.gerenciadorloja.dao;

import java.util.List;

import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public interface ProdutoDAO extends IGenericDAO<ProdutoEntity> {

	/**
	 * Busca os produtos por c�digo de barras
	 * 
	 * @param codigoBarras
	 * 
	 * @return ProdutoEntity
	 * @throws DAOException
	 */
	ProdutoEntity buscarProdutosPeloCodigoBarras(Long codigoBarras) throws DAOException;

	/**
	 * Busca os produtos que est�o dispon�veis para venda por c�digo de barras
	 * 
	 * @param codigoBarras
	 * @Param produtoVendido
	 * @return ProdutoEntity
	 * @throws DAOException
	 */
	ProdutoEntity buscarProdutosPeloCodigoBarras(Long codigoBarras, Boolean produtoVendido) throws DAOException;

	/**
	 * Busca os produtos de determinada venda
	 * 
	 * @param codigoVenda
	 * @return List<ProdutoEntity>
	 * @throws DAOException
	 */
	List<ProdutoEntity> buscarProdutosPelaVenda(Long codigoVenda) throws DAOException;

}