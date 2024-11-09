package com.poly.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.poly.util.JpaUtil;

public class AbstractDAO<Entity> {
	public static final EntityManager entityManager = JpaUtil.getEntityManager();

	@SuppressWarnings("deprecation")
	@Override
	protected void finalize() throws Throwable {
		entityManager.close();
		super.finalize();
	}

	public Entity findById(Class<Entity> clazz, Integer id) {
		return entityManager.find(clazz, id);
	}

	public List<Entity> findAll(Class<Entity> clazz, boolean existIsActive) {
		String entityName = clazz.getSimpleName();
		StringBuilder sql = new StringBuilder();
		sql.append("Select o From ").append(entityName).append(" o");

//		if (existIsActive == true) {
//			sql.append(" where isActive = 1");
//		}
		TypedQuery<Entity> query = entityManager.createQuery(sql.toString(), clazz);
		return query.getResultList();
	}
	


	public List<Entity> findAll(Class<Entity> clazz, boolean existIsActive, int pageNumber, int pageSize) {
		String entityName = clazz.getSimpleName();
		StringBuilder sql = new StringBuilder();
		sql.append("Select o From ").append(entityName).append(" o");

//		if (existIsActive == true) {
//			sql.append(" where isActive = 1");
//		}
		TypedQuery<Entity> query = entityManager.createQuery(sql.toString(), clazz);
		// pageNumber : số trang người dùng nhập vào
		// pageSize : số phần tử tối đa của trang
		// khi người dùng muốn lấy các phần tử ở trang 2 thực hiện query
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
		// SELECT o FROM entity where isActive = 1; chỉ query entity đang hoạt động
		// (isAvtive =1)
	}

	// select o from entity o where o.username = ?0 and o.password = ?1
	// findOne(User.class, sql, "nguyenltt", "1234");
	public Entity findOne(Class<Entity> clazz, String sql, Object... params) {
		TypedQuery<Entity> query = entityManager.createQuery(sql, clazz);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		List<Entity> result = query.getResultList();// tránh trường hợp query không được record nào
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}

	public List<Entity> findMany(Class<Entity> clazz, String sql, Object... params) {
		TypedQuery<Entity> query = entityManager.createQuery(sql, clazz);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findManyByNativeQuery(String sql, Object... params) {
		Query query = entityManager.createNativeQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.getResultList();

	}

	public Entity create(Entity entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			System.out.println("Create succeed");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			System.out.println("Cannot insert entity " + entity.getClass().getSimpleName() + " to DB");
			throw new RuntimeException(e);
		}
	}

	public Entity update(Entity entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(entity);
			entityManager.getTransaction().commit();
			System.out.println("Update succeed");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			System.out.println("Cannot update entity " + entity.getClass().getSimpleName());
			throw new RuntimeException(e);
		}
	}

	public Entity delete(Entity entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			System.out.println("Delete succeed");
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			System.out.println("Cannot delete entity " + entity.getClass().getSimpleName());
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entity> callStored(String nameStored, Map<String, Object> params) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(nameStored);
		params.forEach((key, value) -> query.setParameter(key, value));
		return (List<Entity>) query.getResultList();
	}
}
