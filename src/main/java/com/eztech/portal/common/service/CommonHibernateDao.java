package com.eztech.portal.common.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;

import com.hquery.HibernateMapDaoSupport;

@Deprecated
public class CommonHibernateDao<T> extends HibernateMapDaoSupport<T> {

	@Deprecated
	@SuppressWarnings("unchecked")
	public CommonHibernateDao() {
		super.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public CommonHibernateDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		super.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Deprecated
	public T findBy(String fieldName, Serializable key) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.hquery().where(fieldName, key).getEntity();
	}
	
	@Deprecated
	public List<T> findListBy(String fieldName, Serializable key) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.hquery().where(fieldName, key).listEntity();
	}
}
