package com.hquery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*
 * HibernateMapDao
 * 
 * HibernateMapDao는 하이버네이트 쿼리 결과를 Map 또는 List 타입으로 반환하는 범용 Dao이다.
 * 이를 활용하면 테이블마다 Dao 구현이 불필요해지고, Service 클래스에서 하나의 HibernateMapDao인스턴스로
 * 여러 테이블에 다양한 쿼리를 실행할 수 있다.
 * 
 * 스프링 실행 시 Bean으로 올라가며 사용 예는 아래와 같다.
 * 
	@Service
	public class CodeService {
	
		@Autowired
		private HibernateMapDao mapDao;
		
		@SuppressWarnings({ "rawtypes" })
		public List<Map> list(String C_GRP_CD) throws Exception... {
		...
 */
@Repository
@SuppressWarnings("rawtypes")
public class HibernateMapDao extends HibernateMapDaoSupport {

	@Autowired
	public HibernateMapDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
