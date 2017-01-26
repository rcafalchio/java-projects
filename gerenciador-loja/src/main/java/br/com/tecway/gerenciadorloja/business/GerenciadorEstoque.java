package br.com.tecway.gerenciadorloja.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.dao.EstoqueDAO;
import br.com.tecway.gerenciadorloja.dao.EstoqueDAOImpl;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.EstoqueEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

public class GerenciadorEstoque implements IGerenciadorEstoque {

	@Override
	public void adicionarEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException,
			DAOException {

		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final EstoqueDAO estoqueDAO = new EstoqueDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.findByPk(estoqueTableVO.getProdutoTableVO().getCodigo());

		if (produtoEntity.getEstoques() == null) {
			produtoEntity.setEstoques(new ArrayList<EstoqueEntity>());
		}

		for (int i = 0; i < quantidade; i++) {
			final EstoqueEntity estoqueEntity = new EstoqueEntity();
			estoqueEntity.setDataCadastro(new Date());
			estoqueEntity.setProduto(produtoEntity);
			produtoEntity.getEstoques().add(estoqueEntity);
			estoqueDAO.save(estoqueEntity);
		}

	}

	@Override
	public void removerEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException,
			DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.findByPk(estoqueTableVO.getProdutoTableVO().getCodigo());
		final List<EstoqueEntity> listaEstoques = produtoEntity.getEstoques();
		final List<EstoqueEntity> listaEstoquesRemovidos = new ArrayList<EstoqueEntity>();

		for (EstoqueEntity estoqueEntity : listaEstoques) {
			listaEstoquesRemovidos.add(estoqueEntity);
			quantidade--;
			if (quantidade.equals(0)) {
				break;
			}
		}

		for (EstoqueEntity estoqueEntity : listaEstoquesRemovidos) {
			produtoEntity.getEstoques().remove(estoqueEntity);
		}
		produtoDAO.merge(produtoEntity);
	}

	@Override
	public ProdutoEntity verificarExistenciaProdutos(Integer quantidade, Long codigoBarras) throws BusinessException,
			DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.buscarProdutosPeloCodigoBarras(codigoBarras, Boolean.FALSE);

		if (produtoEntity == null || produtoEntity.getEstoques() == null || produtoEntity.getEstoques().size() == 0) {
			throw new BusinessException("Não existem produtos cadastrados para venda!");
		}

		if (quantidade.compareTo(produtoEntity.getEstoques().size()) > 0) {
			throw new BusinessException("A quantidade de produtos no estoque é de "
					+ produtoEntity.getEstoques().size() + " unidade(s)!");
		}
		return produtoEntity;

	}
}
