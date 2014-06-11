package br.com.tecway.gerenciadorloja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tecway.gerenciadorloja.entity.EstoqueEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public class EstoqueDAOImpl extends GenericDAO<EstoqueEntity> implements EstoqueDAO {

	public EstoqueDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<EstoqueEntity> buscarUnidadesDisponiveis(Integer codigoProduto) throws DAOException {

		List<EstoqueEntity> lista = null;

		try {

			final Query query = entityManager.createNamedQuery("EstoqueEntity.buscarUnidadesDisponiveis");
			query.setParameter("codigo", codigoProduto);
			lista = query.getResultList();

		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}

		return lista;
	}

}
