package br.com.tecway.gerenciadorloja.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("lojaJPA");

	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
}
