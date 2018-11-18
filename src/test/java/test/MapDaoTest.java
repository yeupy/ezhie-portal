package test;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eztech.portal.common.model.TB_CODE;
import com.eztech.portal.system.model.TB_HPROVIDER;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hquery.HibernateMapDao;
import com.hquery.hQueryHelper;
import com.hquery.HibernateMapDaoSupport.hQuery;

public class MapDaoTest extends hQueryHelper {

	@Autowired
	private HibernateMapDao mdao;
	
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void test_hquery() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		String paramName = "C_MDINS_ID";
		Object value = 103L;
		test("hquery(Class<?> entityClass).where(String paramName, Object value).getEntity()",
				paramName,
				value,
				mdao.hquery(TB_HPROVIDER.class).where(paramName, value).getEntity()
			);
		
		hQuery h1 = mdao.hquery(TB_CODE.class);
		hQuery h2 = mdao.hquery(TB_HPROVIDER.class);
		test("h1.list(int ... results)",
				h1.list(0, 5)
			);
		test("h2.list(int ... results)",
				h2.list(0, 5)
			);
	}
	
	public void test_insert() {
		
		/* Test variable */
		TB_HPROVIDER hprovider = new TB_HPROVIDER();
		hprovider.setC_MDINS_NM("1234");
		hprovider.setC_MDINS_OID("1.2.410.100110.10.10000001");
		hprovider.setC_MDINS_POST_NO("42644");
		hprovider.setC_RCORG_NO("10000001");
		hprovider.setC_MDINS_CLS_CD("C001");
		hprovider.setC_ENTY_STS_CD("");
		hprovider.setC_EMR_SYS_CLS_YN('Y');
		hprovider.setC_DMD_SYS_CLS_YN('N');
		hprovider.setC_REG_DTM(new Date(Calendar.getInstance().getTimeInMillis()));
		hprovider.setC_REG_ID("C0HIE");
		hprovider.setC_MDF_DTM(new Date(Calendar.getInstance().getTimeInMillis()));
		hprovider.setC_MDF_ID("C0HIE");
		hprovider.setC_USE_YN('Y');
		
		/* Test cases */
		test("insert(Object entity)",
				hprovider,
				mdao.insert(hprovider)
			);
		
		Map hproviderMap = new ObjectMapper().convertValue(hprovider, Map.class);
		test("insert(Map entityMap)",
				hproviderMap,
				mdao.insert(hproviderMap, TB_HPROVIDER.class)
			);
		
	}
	
	public void test_query() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		/* Test variables */
		String tableQuery = null;
		String whereQuery = null;
		String selectQuery = null;
		String paramName = null;
		Object value = null;
		String[] paramNames = null;
		Object[] values = null;
		
		/* Test cases */
		selectQuery = toSelectSqlString("C_MDINS_ID, C_MDINS_NM, C_RPST_NM");
		tableQuery = "from TB_HPROVIDER ";
		whereQuery = "where C_MDINS_NM like '%병원' and C_RPST_NM in ('백운이', '조명구')";
		test("query(String queryString)",
				selectQuery,
				tableQuery,
				whereQuery,
				mdao.query(selectQuery + tableQuery + whereQuery)
			);
		
		selectQuery = toSelectSqlString(TB_HPROVIDER.class);
		test("query(String queryString)",
				selectQuery,
				tableQuery,
				whereQuery,
				mdao.query(selectQuery + tableQuery + whereQuery)
			);
		
		test("query(String queryString)",
				tableQuery,
				whereQuery,
				mdao.query(tableQuery + whereQuery)
			);
		
		selectQuery = "select a ";
		tableQuery = "from TB_HPROVIDER a";
		test("query(String queryString)",
				selectQuery,
				tableQuery,
				mdao.query(selectQuery + tableQuery, 0, 10)
			);
	}
	
	public void test_list() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		test("hquery(Class<?> entityClass).list(int ... results)",
				mdao.hquery(TB_HPROVIDER.class).list(0, 5)
			);
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, JsonProcessingException, IllegalArgumentException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:test/DaoTest.xml"
		});
		
		MapDaoTest mapDaoTest = context.getBean("mapDaoTest", MapDaoTest.class);
		
		mapDaoTest.test_hquery();
//		mapDaoTest.test_query();
//		mapDaoTest.test_list();
//		mapDaoTest.test_insert();
	}
	
}
