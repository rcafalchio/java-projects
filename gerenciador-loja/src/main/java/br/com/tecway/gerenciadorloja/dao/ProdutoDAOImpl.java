package br.com.tecway.gerenciadorloja.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public class ProdutoDAOImpl extends GenericDAO<ProdutoEntity> implements ProdutoDAO {

	private static final Logger LOGGER = LogManager.getLogger(ProdutoDAOImpl.class);

	public ProdutoDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public ProdutoEntity buscarProdutosPeloCodigoBarras(Long codigoBarras) throws DAOException {

		ProdutoEntity produtoEntity = null;

		try {

			final Query query = entityManager.createNamedQuery("ProdutoEntity.buscarProdutosPeloCodigoBarras");
			query.setParameter("codigoBarras", codigoBarras);
			produtoEntity = (ProdutoEntity) query.getSingleResult();

		} catch (NoResultException e) {
			produtoEntity = null;
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}

		return produtoEntity;
	}

	@Override
	public ProdutoEntity buscarProdutosPeloCodigoBarras(Long codigoBarras, Boolean produtoVendido) throws DAOException {
		ProdutoEntity produtoEntity = null;

		try {

			final Query query = entityManager
					.createNamedQuery("ProdutoEntity.buscarProdutosDisponiveisPeloCodigoBarras");
			query.setParameter("codigoBarras", codigoBarras);
			produtoEntity = (ProdutoEntity) query.getSingleResult();

		} catch (NoResultException e) {
			produtoEntity = null;
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}
		return produtoEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProdutoEntity> buscarProdutosPelaVenda(Long codigoVenda) throws DAOException {
		List<ProdutoEntity> lista = new ArrayList<ProdutoEntity>();

		try {
			final Query query = entityManager.createNamedQuery("ProdutoEntity.buscarProdutosPelaVenda");
			query.setParameter("codigoVenda", codigoVenda);
			lista = query.getResultList();
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}
		return lista;
	}

}
