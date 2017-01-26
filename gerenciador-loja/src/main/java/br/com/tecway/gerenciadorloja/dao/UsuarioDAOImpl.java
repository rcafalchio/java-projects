package br.com.tecway.gerenciadorloja.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;
import br.com.tecway.gerenciadorloja.exception.DAOException;

public class UsuarioDAOImpl extends GenericDAO<UsuarioEntity> implements UsuarioDAO {

	public UsuarioDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public UsuarioEntity buscarUsuarioPorLogin(String login) throws DAOException {

		UsuarioEntity usuarioEntity = null;

		try {

			final Query query = entityManager.createNamedQuery("UsuarioEntity.buscarUsuarioPorLogin");
			query.setParameter("login", login);
			usuarioEntity = (UsuarioEntity) query.getSingleResult();

		} catch (NoResultException e) {
			usuarioEntity = null;
		} catch (Exception e) {
			throw new DAOException("ERRO", e, LOGGER);
		}

		return usuarioEntity;
	}


}
