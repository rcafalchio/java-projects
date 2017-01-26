package br.com.tecway.gerenciadorloja.dao;

import javax.persistence.EntityManager;

import br.com.tecway.gerenciadorloja.entity.MarcaProdutoEntity;

public class MarcaProdutoDAOImpl extends GenericDAO<MarcaProdutoEntity> implements MarcaProdutoDAO {

	public MarcaProdutoDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
