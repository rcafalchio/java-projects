package br.com.tecway.gerenciadorloja.business;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.ApplicationUtils;
import br.com.tecway.gerenciadorloja.utils.ConverterUtils;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

public class GerenciadorProduto implements IGerenciadorProduto {

	@Override
	public void atualizarProduto(ProdutoEntity produtoEntity) throws BusinessException, DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		if (produtoDAO.buscarProdutosPeloCodigoBarras(produtoEntity.getCodigoBarras()) != null) {
			throw new BusinessException("Código de barras já associado a outro produto!");
		}
		produtoDAO.merge(produtoEntity);
	}

	@Override
	public void cadastrarProduto(ProdutoEntity produtoEntity) throws BusinessException, DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity2 = produtoDAO.buscarProdutosPeloCodigoBarras(produtoEntity.getCodigoBarras());
		if (produtoEntity2 != null) {
			throw new BusinessException("Código de barras já associado a outro produto!");
		}
		produtoDAO.save(produtoEntity);
	}

	@Override
	public void excluirProduto(Integer codigo) throws BusinessException, DAOException {
		try {
			final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
			final ProdutoEntity produtoEntity = produtoDAO.findByPk(codigo);
			produtoDAO.delete(produtoEntity);
		} catch (DAOException e) {
			if (ApplicationUtils.verificarException(e.getCause(), ConstraintViolationException.class)) {
				throw new BusinessException(
						"Não é possível excluir este produto pois ele está associado a uma venda ou a um estoque!");
			}
		}
	}

	@Override
	public List<ProdutoVO> buscarProdutosVendidos(Long codigoVenda) throws DAOException, ConverterException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final List<ProdutoEntity> produtos = produtoDAO.buscarProdutosPelaVenda(codigoVenda);
		return ConverterUtils.deProdutoEntityParaProdutoVO(produtos);
	}


}
