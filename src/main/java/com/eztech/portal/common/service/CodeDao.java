package com.eztech.portal.common.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eztech.portal.common.model.TB_CODE;
import com.hquery.HibernateMapDaoSupport;

/*
 * HibernateMapDao
 * 
 * 지정 클래스의 상속없이 Map을 리턴하는 범용 하이버네이트 DAO
 */
@Repository
public class CodeDao extends HibernateMapDaoSupport<TB_CODE> {

	@Autowired
	public CodeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
