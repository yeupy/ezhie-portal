package com.hquery;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.hquery.HibernateMapDaoSupport.hQuery;

/*
 * hQueryHelper
 * 
 * hQueryHelper는 Dao를 Wrapping하여 가장 많이 사용하는 get(), list() 쿼리 메서드를 더 빠르고 간결하게 처리하도록 인터페이스를 지원한다.
 * 이 외 String Query 작성 시 Select 문 작성을 간편화하고 개발자가 새로 작성한 쿼리를 도입 전 스프링을 구동하지 않는 단일 테스트에서 분석적인 테스트를 할 수 있도록 test 유틸을 제공한다.
 */
public class hQueryHelper {

	@SuppressWarnings("rawtypes")
	private hQuery hquery;
	
	/*
	 * hQueryHelper 초기화.
	 */
	public hQueryHelper() {
	}
	
	/*
	 * dao를 위해 hQueryHelper 초기화.
	 */
	@SuppressWarnings("rawtypes")
	private hQueryHelper(HibernateMapDaoSupport dao) {
		this.hquery = dao.hquery();
	}
	
	/*
	 * dao에 entityClass 지정하고 hQueryHelper 초기화.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private hQueryHelper(HibernateMapDaoSupport dao, Class<?> entityClass) {
		this.hquery = dao.hquery(entityClass);
	}
	
	/*
	 * dao를 위해 hQueryHelper 초기화.
	 */
	@SuppressWarnings("rawtypes")
	public static hQueryHelper helpDao(HibernateMapDaoSupport dao) {
		return new hQueryHelper(dao);
	}
	
	/*
	 * dao에 entityClass 지정하여 hQueryHelper 초기화.
	 */
	@SuppressWarnings("rawtypes")
	public static hQueryHelper helpDao(HibernateMapDaoSupport dao, Class<?> entityClass) {
		return new hQueryHelper(dao, entityClass);
	}
	
	/*
	 * columnString을 SQL SELECT 문으로 변환. 
	 */
	public static String toSelectSqlString(String columnString) {
		return toSelectSqlString(columnString, columnString);
	}
	
	/*
	 * columnString을 aliasString을 포함한 SQL SELECT 문으로 변환. 
	 */
	public static String toSelectSqlString(String columnString, String aliasString) {
		String[] _columns = columnString.split("\\s*,\\s*");
		String[] _alias = aliasString.split("\\s*,\\s*");
		if (_columns.length != _alias.length) {
			throw new IllegalArgumentException("Length of columns array must match length of alias array");
		}
		ProjectionList projection = Projections.projectionList();
		int size = _columns.length;
		for(int i = 0; i < size; i++) {
			projection.add(Projections.property(_columns[i]).as(_alias[i]));
		}
		return "select " + projection.toString().replaceAll("\\[|\\]", "") + " ";
	}
	
	/*
	 * entityClass의 모든 속성을 SQL SELECT 문으로 변환. 
	 */
	public static String toSelectSqlString(Class<?> entityClass) {
		ProjectionList projection = Projections.projectionList();
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field: fields) {
			if(field.getDeclaredAnnotations().length == 0) { continue; }
			String fname = field.getName();
			projection.add(Projections.property(fname).as(fname));
		}
		return "select " + projection.toString().replaceAll("\\[|\\]", "") + " ";
	}
	
	/*
	 * WHERE에 paramName = value 조건 추가하여 하나의 쿼리 결과를 반환.
	 */
	@SuppressWarnings("rawtypes")
	public Map get(String paramName, Object value) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.hquery.where(paramName, value).get();
	}
	
	/*
	 * SELECT에 columnString 추가, WHERE에 paramName = value 조건 추가하여 하나의 쿼리 결과를 반환.
	 */
	@SuppressWarnings("rawtypes")
	public Map get(String columnString, String paramName, Object value) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.hquery.select(columnString).where(paramName, value).get();
	}
	
	/*
	 * WHERE에 paramNames[n] = values[n] 조건 추가하여 하나의 쿼리 결과를 반환.
	 */
	@SuppressWarnings("rawtypes")
	public Map get(String[] paramNames, Object[] values) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.hquery.where(paramNames, values).get();
	}
	
	/*
	 * SELECT에 columnString 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 하나의 쿼리 결과를 반환.
	 */
	@SuppressWarnings("rawtypes")
	public Map get(String columnString, String[] paramNames, Object[] values) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.hquery.select(columnString).where(paramNames, values).get();
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listAsc(orderString, null, new String[] {}, new Object[] {}, results);
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가, SELECT에 columnString 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, String columnString, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listAsc(orderString, columnString, new String[] {}, new Object[] {}, results);
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가, WHERE에 paramName = value 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, String paramName, Object value, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listAsc(orderString, null, new String[] {paramName}, new Object[] {value}, results);
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가, SELECT에 columnString 추가, WHERE에 paramName = value 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, String columnString, String paramName, Object value, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listAsc(orderString, columnString, new String[] {paramName}, new Object[] {value}, results);
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, String[] paramNames, Object[] values, int ... results) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.listAsc(orderString, null, paramNames, values, results);
	}
	
	/*
	 * ORDER BY에 orderString 오름차순 정렬 추가, SELECT에 columnString 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listAsc(String orderString, String columnString, String[] paramNames, Object[] values, int ... results) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.list(orderString, true, columnString, paramNames, values, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listDesc(orderString, null, new String[] {}, new Object[] {}, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가, SELECT에 columnString 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, String columnString, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listDesc(orderString, columnString, new String[] {}, new Object[] {}, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가, WHERE에 paramName = value 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, String paramName, Object value, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listDesc(orderString, null, new String[] {paramName}, new Object[] {value}, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가, SELECT에 columnString 추가, WHERE에 paramName = value 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, String columnString, String paramName, Object value, int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return this.listDesc(orderString, columnString, new String[] {paramName}, new Object[] {value}, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, String[] paramNames, Object[] values, int ... results) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.listDesc(orderString, null, paramNames, values, results);
	}
	
	/*
	 * ORDER BY에 orderString 내림차순 정렬 추가, SELECT에 columnString 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> listDesc(String orderString, String columnString, String[] paramNames, Object[] values, int ... results) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return this.list(orderString, false, columnString, paramNames, values, results);
	}
	
	/*
	 * ORDER BY에 orderString 정렬 추가, SELECT에 columnString 추가, WHERE에 paramNames[n] = values[n] 조건 추가하여 results[0]에서 results[1]만큼 쿼리 결과 반환.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> list(String orderString, boolean asc, String columnString, String[] paramNames, Object[] values, int ... results) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		hQuery _q = this.hquery;
		if(columnString != null) {
			_q.select(columnString);
		}
		
		Order[] orders = new Order[] {};
		if(orderString != null) {
			String[] orderStr = orderString.split("\\s*,\\s*");
			int size = orderStr.length;
			orders = new Order[size];
			for(int i = 0; i < size; i++) {
				orders[i] = asc ? Order.asc(orderStr[i]) : Order.desc(orderStr[i]);
			}
		}
		
		return _q.where(paramNames, values).order(orders).list(results);
	}
	
	/*
	 * 제목 methodName 지정하고 results[n] 파라미터 지정하여 results[last] 쿼리 결과 보고.
	 */
	public static void test(String methodName, Object ... results) {
		String bar = "======================";
		String title = "=== TESTING METHOD: " + methodName + " " + bar + bar;
		System.out.println(title);
		
		int len = results.length;
		for(int i = 0; i < len; i++) {
			System.out.print(i < len - 1 ? "Param["+ i +"]: " : "Result: ");
			if(results[i] instanceof Object[]) {
				System.out.println(Arrays.toString((Object[])results[i]));
			} else if(results[i] instanceof List) {
				List<?> list = (List<?>)results[i];
				Iterator<?> itr = list.iterator();
				int j = 0;
				while(itr.hasNext() && j < 10) {
					System.out.println((j > 0 ? "\t" : "") + itr.next());
					j++;
				}
				
				int totalRest = list.size() - j;
				if(j == 10 && totalRest > 0) {
					System.out.println("... 외 " + totalRest + " 개");
				}
			} else {
				System.out.println(results[i]);
			}
		}
		
		int titleLength = title.length();
		String end = "";
		while(end.length() < titleLength) {
			end += bar.charAt(bar.length() - 1);
		}
		System.out.println(end);
	}
}
