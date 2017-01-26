package com.leilaopecuario.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("geraBanco");

	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
}
