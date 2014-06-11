package br.com.tecway.gerenciadorloja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tecway.gerenciadorloja.entity.EstoqueCasaEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 27/11/2013
 */
public class EstoqueCasaDAOImpl extends GenericDAO<EstoqueCasaEntity> implements EstoqueCasaDAO {

	public EstoqueCasaDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<EstoqueCasaEntity> buscarUnidadesDisponiveis(Integer codigoProduto) throws DAOException {

		List<EstoqueCasaEntity> lista = null;

		try {

			final Query query = entityManager.createNamedQuery("EstoqueCasaEntity.buscarUnidadesDisponiveis");
			query.setParameter("codigo", codigoProduto);
			lista = query.getResultList();

		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}

		return lista;
	}

}
