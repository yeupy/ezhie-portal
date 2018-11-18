package com.eztech.portal.system.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eztech.portal.system.model.TB_HPROVIDER;
import com.hquery.HibernateMapDaoSupport;

@Repository
public class HproviderDao extends HibernateMapDaoSupport<TB_HPROVIDER> {

	@Autowired
	public HproviderDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
