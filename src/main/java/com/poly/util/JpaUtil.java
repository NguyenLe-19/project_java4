package com.poly.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;

	public static EntityManager getEntityManager() {
		if (factory == null || factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("asmjv4");
		}
		return factory.createEntityManager();
	}

	public static void shutDowm() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
		factory = null;
	}
}
