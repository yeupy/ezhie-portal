package com.hquery;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/*
 * HibernateMapDaoSupport
 * 
 * HibernateMapDao의 부모 클래스로 HibernateMapDao 또는 Entity 모델을 지정한 Dao의 쿼리 결과를 Map 또는 List로 반환한다.
 * hQuery라는 이너 클래스를 포함하고 있으며, 이를 이용해 HibernateMapDaoSupport를 상속받은 Dao가 쉽게 하이버네이트 쿼리를 실행하도록 지원한다.
 * 
 * 특정 Entity 모델을 쿼리 결과 타입으로 지정한 Dao가 HibernateMapDaoSupport를 상속하는 예는 아래와 같다.
 * 
	@Repository
	public class HproviderDao extends HibernateMapDaoSupport<TB_HPROVIDER> {
	
		@Autowired
		public HproviderDao(SessionFactory sessionFactory) {
			super(sessionFactory);
		}
	}
 */
public abstract class HibernateMapDaoSupport<T> extends HibernateDaoSupport {
	
	protected Class<?> entityClass;
	
	protected ObjectMapper mapper = new ObjectMapper()
			.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
			.setSerializationInclusion(Include.NON_NULL)
			.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
	
	@SuppressWarnings("unchecked")
	public HibernateMapDaoSupport() {
		Type generic = this.getClass().getGenericSuperclass();
		this.entityClass = generic.equals(HibernateMapDaoSupport.class) ? null : (Class<T>) ((ParameterizedType) generic).getActualTypeArguments()[0];
	}
	
	@SuppressWarnings("unchecked")
	public HibernateMapDaoSupport(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		Type generic = this.getClass().getGenericSuperclass();
		this.entityClass = generic.equals(HibernateMapDaoSupport.class) ? null : (Class<T>) ((ParameterizedType) generic).getActualTypeArguments()[0];
	}
	
	/*
	 * entityClass가 지정된 Dao의 hQuery 초기화.
	 */
	public hQuery hquery() {
		return new hQuery(this, this.entityClass);
	}
	
	/*
	 * entityClass가 지정된 Dao의 hQuery에 alias를 지정하여 초기화.
	 */
	public hQuery hquery(String alias) {
		return new hQuery(this, this.entityClass, alias);
	}
	
	/*
	 * 범용 또는 일반 Dao의 entityClass를 임시로 지정하여 hQuery를 초기화.
	 */
	public hQuery hquery(Class<?> entityClass) {
		return new hQuery(this, entityClass);
	}
	
	/*
	 * 범용 또는 일반 Dao의 Entity 클래스와 alias를 임시로 지정하여 hQuery를 초기화.
	 */
	public hQuery hquery(Class<?> entityClass, String alias) {
		return new hQuery(this, entityClass, alias);
	}
	
	/*
	 * queryString으로 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> query(String queryString, int ... results) {
		return this.query(queryString, new String[] {}, new Object[] {}, results);
	}
	
	/*
	 * queryString에 paramName = value NamedParameter를 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 * 
	 * 예) hdao.query("from TB_HPROVIDER where C_RPST_NM = :C_RPST_NM", "C_RPST_NM", "테스터", 0, 5);
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> query(String queryString, String paramName, Object value, int ... results) {
		return this.query(queryString, new String[] {paramName}, new Object[] {value}, results);
	}
	
	/*
	 * queryString에 paramNames[n] = values[n] NamedParameter를 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 * 
	 * 예) hdao.query(tableQuery + "where C_MDINS_OID like :C_MDINS_OID and C_RPST_NM in :C_RPST_NM", new String[] {"C_MDINS_OID", "C_RPST_NM"}, new Object[] {"1.2.410%", new String[] {"백운이", "조명구"}})
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> query(final String queryString, final String[] paramNames, final Object[] values, final int ... results) {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		
		final HibernateTemplate hp = this.getHibernateTemplate();
		final Class entityClass = this.entityClass;
		
		return hp.executeWithNativeSession(new HibernateCallback<List<Map>>() {
			@Override
			public List<Map> doInHibernate(Session session) throws HibernateException {
				
				Query queryObject = null;
				
				String prefix = queryString.trim().toLowerCase();
				if(prefix.startsWith("select")) {
					queryObject = session.createQuery(queryString);
				} else if(prefix.startsWith("from") && entityClass != null) {
					queryObject = session.createQuery(hQueryHelper.toSelectSqlString(entityClass) + queryString);
				} else {
					return null;
				}
				
				queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				
				prepareQuery(hp, queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						applyNamedParameterToQuery(hp, queryObject, paramNames[i], values[i]);
					}
				}
				return queryObject.list();
			}

			private void applyNamedParameterToQuery(HibernateTemplate hp, Query queryObject, String paramName, Object value) {
				if (value instanceof Collection) {
					queryObject.setParameterList(paramName, (Collection<?>) value);
				} else if (value instanceof Object[]) {
					queryObject.setParameterList(paramName, (Object[]) value);
				} else {
					queryObject.setParameter(paramName, value);
				}
			}

			private void prepareQuery(HibernateTemplate hp, Query queryObject) {
				if (hp.isCacheQueries()) {
					queryObject.setCacheable(true);
					if (hp.getQueryCacheRegion() != null) {
						queryObject.setCacheRegion(hp.getQueryCacheRegion());
					}
				}
				if (hp.getFetchSize() > 0) {
					queryObject.setFetchSize(hp.getFetchSize());
				}
				if (hp.getMaxResults() > 0) {
					queryObject.setMaxResults(hp.getMaxResults());
				}
				
				if(results.length >= 2) {
					queryObject.setFirstResult(results[0]);
					queryObject.setMaxResults(results[1]);
				}

				SessionHolder sessionHolder =
						(SessionHolder) TransactionSynchronizationManager.getResource(getSessionFactory());
				if (sessionHolder != null && sessionHolder.hasTimeout()) {
					queryObject.setTimeout(sessionHolder.getTimeToLiveInSeconds());
				}
			}
		});
	}
	
	/*
	 * 조건을 포함한 crit으로 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> list(DetachedCriteria crit, int ... results) {
		if(results.length >= 2) {
			return (List<Map>) this.getHibernateTemplate().findByCriteria(crit, results[0], results[1]);
		}
		return (List<Map>) this.getHibernateTemplate().findByCriteria(crit);
	}
	
	/*
	 * entityOrMap을 entityClass로 변환하여 INSERT 쿼리 실행.
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public Serializable insert(Object entityOrMap, Class<?> entityClass) {
		return this.getHibernateTemplate().save(entityOrMap instanceof Map ? mapper.convertValue(entityOrMap, entityClass) : entityOrMap);
	}
	
	/*
	 * entityOrMap을 지정된 entityClass로 변환하여 INSERT 쿼리 실행.
	 */
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public Serializable insert(Object entityOrMap) {
		return this.insert(entityOrMap, this.entityClass);
	}
	
	/*
	 * entityOrMap을 entityClass로 변환하여UPDATE 쿼리 실행.
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public void update(Object entityOrMap, Class<?> entityClass) throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException {
		List<String> _ids = new ArrayList<>();
		List<Object> _values = new ArrayList<>();
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field: fields) {
			if(field.getDeclaredAnnotation(Id.class) == null) { continue; }
			_ids.add(field.getName());
			field.setAccessible(true);
			if(entityOrMap instanceof Map) {
				_values.add(((Map) entityOrMap).get(field.getName()));
			} else {
				_values.add(field.get(entityOrMap));
			}
		}
		String[] ids =_ids.toArray(new String[_ids.size()]);
		Object[] values = _values.toArray();
		
		Map mergedMap = mapper.readerForUpdating(this.hquery().where(ids, values).get()).readValue(mapper.writeValueAsString(entityOrMap));
		this.getHibernateTemplate().update(mapper.convertValue(mergedMap, entityClass));
	}
	
	/*
	 * entityOrMap을 지정된 entityClass로 변환하여UPDATE 쿼리 실행.
	 */
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public void update(Object entityOrMap) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InstantiationException, IOException {
		this.update(entityOrMap, this.entityClass);
	}
	
	/*
	 * entityOrMap을 entityClass로 변환하여 DELETE 쿼리 실행.
	 */
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public void delete(Object entity, Class<?> entityClass) {
		this.getHibernateTemplate().delete(entity instanceof Map ? mapper.convertValue(entity, entityClass) : entity);
	}
	
	/*
	 * entityOrMap을 지정된 entityClass로 변환하여 DELETE 쿼리 실행.
	 */
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public void delete(Object entity) {
		this.delete(entity, this.entityClass);
	}
	
	/*
	 * hQuery
	 * 
	 * HibernateMapDaoSupport의 이너 클래스이며 핵심으로서,
	 * HibernateMapDao 객체 또는 이를 상속받은 Dao 객체가 Query String 및 Programmatic 하이버네이트 쿼리를
	 * 아래의 예처럼 쉽게 실행하도록 인터페이스를 제공한다.
	 * 
			mapDao.hquery(TB_HPROVIDER.class)
			.select("C_MDINS_ID, C_RPST_NM, C_MDINS_ADDR")
			.where("C_RPST_NM", "테스터")
			.list();
	 *
	 * hQuery는 select() 나 where() 등 쿼리 메소드의 파라미터로서 개발자 편의를 위해 String 등 Primitive 타입의 값을 받지만,
	 * 동시에 Projection, Criterion 등 하이버네이트의 조건 클래스 타입 또한 수용하여 하이버네이트와 확장성을 유지한다.
	 */
	public class hQuery {

		private ProjectionList projection = Projections.projectionList();
		private DetachedCriteria criteria;
		
		@SuppressWarnings("rawtypes")
		private HibernateMapDaoSupport dao;
		
		private Class<?> entityClass;
		
		/*
		 * dao, entityClass를 지정하여 hQuery를 초기화.
		 */
		@SuppressWarnings("rawtypes")
		public hQuery(HibernateMapDaoSupport dao, Class<?> entityClass) {
			this.dao = dao;
			this.entityClass = entityClass;
			this.criteria = entityClass == null ? null : DetachedCriteria.forClass(entityClass);
		}
		
		/*
		 * dao, entityClass, alias를 지정하여 hQuery를 초기화.
		 */
		@SuppressWarnings("rawtypes")
		public hQuery(HibernateMapDaoSupport dao, Class<?> entityClass, String alias) {
			this.dao = dao;
			this.entityClass = entityClass;
			this.criteria = entityClass == null ? null : DetachedCriteria.forClass(entityClass, alias);
		}

		/*
		 * SELECT에 지정된 entityClass의 전체 속성 추가.
		 */
		private hQuery select() {
			Field[] fields = this.entityClass.getDeclaredFields();
			for(Field field: fields) {
				if(field.getDeclaredAnnotations().length == 0) { continue; }
				String fname = field.getName();
				this.projection.add(Projections.property(fname).as(fname));
			}
			return this;
		}
		
		/*
		 * SELECT에 columnString 추가.
		 */
		public hQuery select(String columnString) {
			this.select(columnString, columnString);
			return this;
		}
		
		/*
		 * SELECT에 columnString을 aliasString와 함께 추가.
		 */
		public hQuery select(String columnString, String aliasString) {
			String[] _columns = columnString.split("\\s*,\\s*");
			String[] _alias = aliasString.split("\\s*,\\s*");
			
			if (_columns.length != _alias.length) {
				throw new IllegalArgumentException("Length of columns array must match length of alias array");
			}
			
			int size = _columns.length;
			for(int i = 0; i < size; i++) {
				this.projection.add(Projections.property(_columns[i]).as(_alias[i]));
			}
			return this;
		}
		
		/*
		 * SELECT에  하이버네이트 projections 추가.
		 */
		public hQuery select(Projection ... projections) {
			for(Projection _p: projections) {
				this.projection.add(_p);
			}
			return this;
		}
		
		/*
		 * SELECT에  subQuery 추가.
		 */
		public hQuery selectSql(String subQuery, String aliasString) {
			Projection _sql = Projections.sqlProjection("(" + subQuery + ") as " + aliasString, new String[] {aliasString}, new org.hibernate.type.Type[] {StringType.INSTANCE});
			return this.select(_sql);
		}
		
		/*
		 * WHERE에 하이버네이트 Criterion 조건 추가.
		 */
		public hQuery where(Criterion ... criterions) {
			if(this.criteria != null) {
				for(Criterion _crit: criterions) {
					this.criteria.add(_crit);
				}
			}
			return this;
		}
		
		/*
		 * WHERE에 paramName = value 조건 추가.
		 */
		public hQuery where(String paramName, Object value) {
			if(value == null) {
				return this.where(Restrictions.isNull(paramName));
			}
			return this.where(Restrictions.eq(paramName, value));
		}
		
		/*
		 * WHERE에 paramNames[n] = values[n] 조건 추가.
		 */
		public hQuery where(String[] paramNames, Object[] values) {
			if (paramNames.length != values.length) {
				throw new IllegalArgumentException("Length of paramNames array must match length of values array");
			}
			int length = paramNames.length;
			for(int i = 0; i < length; i++) {
				this.where(paramNames[i], values[i]);
			}
			return this;
		}
		
		/*
		 * ORDER BY에 하이버네이트 Order 정렬 추가.
		 */
		public hQuery order(Order ... orders) {
			for(Order _ord: orders) {
				this.criteria.addOrder(_ord);
			}
			return this;
		}
		
		/*
		 * ORDER BY에 propertyName 오름차순 정렬 추가.
		 */
		public hQuery orderAsc(String propertyName) {
			this.criteria.addOrder(Order.asc(propertyName));
			return this;
		}
		
		/*
		 * ORDER BY에 propertyName 내림차순 정렬 추가.
		 */
		public hQuery orderDesc(String propertyName) {
			this.criteria.addOrder(Order.desc(propertyName));
			return this;
		}
		
		/*
		 * TABLE에 alias 지정하여 joinColumn 이너조인.
		 */
		public hQuery join(String joinColumn, String alias) {
			this.criteria.createAlias(joinColumn, alias, JoinType.INNER_JOIN);
			return this;
		}
		
		/*
		 * TABLE에 alias와  withClause 조건 지정하여 joinColumn 이너조인.
		 */
		public hQuery join(String joinColumn, String alias, Criterion withClause) {
			this.criteria.createAlias(joinColumn, alias, JoinType.INNER_JOIN, withClause);
			return this;
		}
		
		/*
		 * TABLE에 alias 지정하여 joinColumn 레프트조인.
		 */
		public hQuery leftJoin(String joinColumn, String alias) {
			this.criteria.createAlias(joinColumn, alias, JoinType.LEFT_OUTER_JOIN);
			return this;
		}
		
		/*
		 * TABLE에 alias와  withClause 조건 지정하여 joinColumn 레프트조인.
		 */
		public hQuery leftJoin(String joinColumn, String alias, Criterion withClause) {
			this.criteria.createAlias(joinColumn, alias, JoinType.LEFT_OUTER_JOIN, withClause);
			return this;
		}
		
		/*
		 * TABLE에 alias 지정하여 joinColumn 라이트조인.
		 */
		public hQuery rightJoin(String joinColumn, String alias) {
			this.criteria.createAlias(joinColumn, alias, JoinType.RIGHT_OUTER_JOIN);
			return this;
		}
		
		/*
		 * TABLE에 alias와  withClause 조건 지정하여 joinColumn 라이트조인.
		 */
		public hQuery rightJoin(String joinColumn, String alias, Criterion withClause) {
			this.criteria.createAlias(joinColumn, alias, JoinType.RIGHT_OUTER_JOIN, withClause);
			return this;
		}
		
		/*
		 * TABLE에 alias 지정하여 joinColumn 풀조인.
		 */
		public hQuery fullJoin(String joinColumn, String alias) {
			this.criteria.createAlias(joinColumn, alias, JoinType.FULL_JOIN);
			return this;
		}
		
		/*
		 * TABLE에 alias와  withClause 조건 지정하여 joinColumn 풀조인.
		 */
		public hQuery fullJoin(String joinColumn, String alias, Criterion withClause) {
			this.criteria.createAlias(joinColumn, alias, JoinType.FULL_JOIN, withClause);
			return this;
		}
		
		/*
		 * 조회 조건을 포함한 내부 DetachedCriteria 참조 반환.
		 */
		public DetachedCriteria criteria() {
			return this.criteria;
		}
		
		/*
		 * results[0]에서 results[1]만큼 쿼리 결과 반환.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Map> list(int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
			if(this.projection.getLength() == 0) {
				this.select();
			}
			this.criteria.setProjection(this.projection).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return dao.list(this.criteria, results);
		}
		
		/*
		 * results[0]에서 results[1]만큼 지정된 entityClass 타입으로 쿼리 결과 반환.
		 */
		@SuppressWarnings("unchecked")
		public List<T> listEntity(int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
			if(this.projection.getLength() == 0) {
				this.select();
			}
			this.criteria.setProjection(this.projection).setResultTransformer(Transformers.aliasToBean(this.entityClass));
			return dao.list(this.criteria, results);
		}
		
		/*
		 * 하나의 쿼리 결과 반환.
		 */
		@SuppressWarnings("rawtypes")
		public Map get() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
			List<Map> list = this.list(0, 1);
			if(list.size() > 0) {
				return list.get(0);
			}
			return null;
		}
		
		/*
		 * 지정된 entityClass 타입으로 하나의 쿼리 결과 반환.
		 */
		public T getEntity() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
			List<T> list = this.listEntity(0, 1);
			if(list.size() > 0) {
				return list.get(0);
			}
			return null;
		}
		
		/*
		 * SELECT를 포함한 내부 ProjectionList 참조 반환.
		 */
		public ProjectionList projection() {
			return projection;
		}

	}
}
