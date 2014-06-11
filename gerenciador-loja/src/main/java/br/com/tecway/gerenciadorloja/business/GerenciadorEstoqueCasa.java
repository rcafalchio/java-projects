package br.com.tecway.gerenciadorloja.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.dao.EstoqueCasaDAO;
import br.com.tecway.gerenciadorloja.dao.EstoqueCasaDAOImpl;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.entity.EstoqueCasaEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

public class GerenciadorEstoqueCasa implements IGerenciadorEstoqueCasa {

	@Override
	public void adicionarEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException,
			DAOException {

		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final EstoqueCasaDAO estoqueDAO = new EstoqueCasaDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.findByPk(estoqueTableVO.getProdutoTableVO().getCodigo());

		if (produtoEntity.getEstoquesCasa() == null) {
			produtoEntity.setEstoquesCasa(new ArrayList<EstoqueCasaEntity>());
		}

		for (int i = 0; i < quantidade; i++) {
			final EstoqueCasaEntity estoqueEntity = new EstoqueCasaEntity();
			estoqueEntity.setDataCadastro(new Date());
			estoqueEntity.setProduto(produtoEntity);
			produtoEntity.getEstoquesCasa().add(estoqueEntity);
			estoqueDAO.save(estoqueEntity);
		}

	}

	@Override
	public void removerEstoque(EstoqueTableVO estoqueTableVO, Integer quantidade) throws BusinessException,
			DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.findByPk(estoqueTableVO.getProdutoTableVO().getCodigo());
		final List<EstoqueCasaEntity> listaEstoques = produtoEntity.getEstoquesCasa();
		final List<EstoqueCasaEntity> listaEstoquesRemovidos = new ArrayList<EstoqueCasaEntity>();

		for (EstoqueCasaEntity estoqueEntity : listaEstoques) {
			listaEstoquesRemovidos.add(estoqueEntity);
			quantidade--;

			if (quantidade.equals(0)) {
				break;
			}
		}

		for (EstoqueCasaEntity estoqueEntity : listaEstoquesRemovidos) {
			produtoEntity.getEstoquesCasa().remove(estoqueEntity);
		}
		produtoDAO.merge(produtoEntity);

	}

	@Override
	public ProdutoEntity verificarExistenciaProdutos(Integer quantidade, Long codigoBarras) throws BusinessException,
			DAOException {

		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.buscarProdutosPeloCodigoBarras(codigoBarras, Boolean.FALSE);

		if (produtoEntity == null || produtoEntity.getEstoquesCasa() == null
				|| produtoEntity.getEstoquesCasa().size() == 0) {
			throw new BusinessException("Não existem produtos cadastrados para venda!");
		}

		if (quantidade.compareTo(produtoEntity.getEstoquesCasa().size()) > 0) {
			throw new BusinessException("A quantidade de produtos no estoque é de "
					+ produtoEntity.getEstoquesCasa().size() + " unidade(s)!");
		}

		return produtoEntity;

	}
}
