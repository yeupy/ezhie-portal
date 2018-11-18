package test;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eztech.portal.common.model.TB_CODE;
import com.eztech.portal.common.service.CodeDao;
import com.eztech.portal.system.model.TB_HPROVIDER;
import com.eztech.portal.system.service.HproviderDao;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hquery.HibernateMapDao;
import com.hquery.hQueryHelper;

public class DaoTest extends hQueryHelper {

	@Autowired
	private HproviderDao hdao;
	
	@Autowired
	private CodeDao cdao;

	@SuppressWarnings("unchecked")
	public void test_get() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		/* Test variables */
		String paramName = null;
		Object value = null;
		
		/* Test cases */
		
		// get() test
		paramName = "C_MDINS_ID";
		value = 103L;
		test("hquery().where(String paramName, Object value).get()",
				paramName,
				value,
				hdao.hquery().where(paramName, value).get()
			);
		
		test("hquery().where(String paramName, Object value).get()",
				paramName,
				value,
				hdao.hquery().where(paramName, value).getEntity()
			);
		
	}
	
	public void test_list() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		/* Test variables */
		String paramName = null;
		Object value = null;
		String[] paramNames = null;
		Object[] values = null;
		String columns = null;
		Order[] orders = null;
		Criterion[] criterions = null;
		
		/* Test cases */
		
		// list() test
		test("hquery().list()",
				hdao.hquery().list()
			);
		
		test("hquery().list()",
				hdao.hquery().listEntity()
			);
		
		test("hquery().list(int ... results)",
				0,
				5,
				hdao.hquery().list(0, 5)
			);
		
		// select() test
		columns = "C_MDINS_ID, C_MDINS_NM, C_RPST_NM";
		test("hquery().select(String columnString).list(int ... results)",
				columns,
				0,
				5,
				hdao.hquery().select(columns).list(0, 5)
			);
		
		// where() test
		columns = "C_MDINS_ID, C_RPST_NM";
		paramName = "C_RPST_NM";
		value = "테스터";
		test("hquery().select(String columnString).where(String paramName, Object value).list(int ... results)",
				columns,
				paramName,
				value,
				0,
				5,
				hdao.hquery().select(columns).where(paramName, value).list(0, 5)
			);
		
		columns = "C_MDINS_ID, C_RPST_NM, C_MDINS_ADDR";
		paramNames = new String[] {"C_RPST_NM", "C_MDINS_ADDR"};
		values = new String[] {"테스터", "테스트 주소1"};
		test("hquery().select(String columnString).where(String[] paramNames, Object[] values).list(int ... results)",
				columns,
				paramNames,
				values,
				0,
				5,
				hdao.hquery().select(columns).where(paramNames, values).list(0, 5)
			);
		
		columns = "C_MDINS_ID, C_RPST_NM, C_MDINS_ADDR";
		paramNames = new String[] {"C_RPST_NM", "C_MDINS_ADDR"};
		values = new String[] {"테스터", "테스트 주소1"};
		criterions = new Criterion[] {
				Restrictions.eq(paramNames[0], values[0]),
				Restrictions.eq(paramNames[1], values[1])
		};
		
		test("hquery().select(String columnString).where(Criterion criterion).list(int ... results)",
				columns,
				criterions[0],
				0,
				5,
				hdao.hquery().select(columns).where(criterions[0]).list(0, 5)
			);
		
		test("hquery().select(String columnString).where(Criterion criterion1).where(Criterion criterion2).list(int ... results)",
				columns,
				criterions[0],
				criterions[1],
				0,
				5,
				hdao.hquery().select(columns).where(criterions[0]).where(criterions[1]).list(0, 5)
			);
		
		test("hquery().select(String columnString).where(Criterion ... criterions).list(int ... results)",
				columns,
				criterions[0],
				criterions[1],
				0,
				5,
				hdao.hquery().select(columns).where(criterions[0], criterions[1]).list(0, 5)
			);
		
		
		test("hquery().select(String columnString).where(Criterion[] criterions).list(int ... results)",
				columns,
				criterions,
				0,
				5,
				hdao.hquery().select(columns).where(criterions).list(0, 5)
			);
		
		// order() test
		columns = "C_MDINS_ID, C_RPST_NM, C_MDF_DTM, C_ENTY_STS_CD";
		orders = new Order[] {
				Order.desc("C_ENTY_STS_CD"),
				Order.asc("C_MDF_DTM")
		};
		test("hquery().select(String columnString).order(Order order).list(int ... results)",
				columns,
				orders[0],
				0,
				5,
				hdao.hquery().select(columns).order(orders[0]).list(0, 5)
			);
		
		test("hquery().select(String columnString).order(Order order1).order(Order order2).list(int ... results)",
				columns,
				orders[0],
				orders[1],
				0,
				10,
				hdao.hquery().select(columns).order(orders[0]).order(orders[1]).list(0, 10)
			);
		
		test("hquery().select(String columnString).order(Order ... orders).list(int ... results)",
				columns,
				orders[0],
				orders[1],
				0,
				5,
				hdao.hquery().select(columns).order(orders[0], orders[1]).list(0, 10)
			);
		
		test("hquery().select(String columnString).order(Order[] orders).list(int ... results)",
				columns,
				orders,
				0,
				5,
				hdao.hquery().select(columns).order(orders).list(0, 10)
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
				hdao.query(selectQuery + tableQuery + whereQuery)
			);
		
		test("query(String queryString, int ... results)",
				selectQuery,
				tableQuery,
				0,
				5,
				hdao.query(tableQuery, 0, 5)
			);
		
		whereQuery = "where C_RPST_NM = :C_RPST_NM";
		paramName = "C_RPST_NM";
		value = "테스터";
		test("query(String queryString, String paramName, Object value, int ... results)",
				selectQuery,
				tableQuery,
				whereQuery,
				paramName,
				value,
				0,
				5,
				hdao.query(selectQuery + tableQuery + whereQuery, paramName, value, 0, 5)
			);
		
		values = new String[] {"백운이", "조명구", "테스터"};
		selectQuery = "select distinct C_RPST_NM as C_RPST_NM ";
		whereQuery = "where C_RPST_NM in :C_RPST_NM";
		test("query(String queryString, String paramName, Object[] values)",
				selectQuery,
				tableQuery,
				whereQuery,
				paramName,
				values,
				hdao.query(selectQuery + tableQuery + whereQuery, paramName, values)
			);
		
		selectQuery = toSelectSqlString("count(C_MDINS_ID), C_RPST_NM", "CNT, C_RPST_NM");
		whereQuery = "where C_MDINS_OID like :C_MDINS_OID and C_RPST_NM in :C_RPST_NM group by C_RPST_NM order by CNT desc";
		paramNames = new String[] {"C_MDINS_OID", "C_RPST_NM"};
		values = new Object[] {"1.2.410%", new String[] {"백운이", "조명구", "테스터"}};
		test("query(String queryString, String[] paramNames, Object[] values)",
				selectQuery,
				tableQuery,
				whereQuery,
				paramNames,
				values,
				hdao.query(selectQuery + tableQuery + whereQuery, paramNames, values)
			);
		
		test("query(String queryString, String[] paramNames, Object[] values)",
				hdao.query(tableQuery + "where C_MDINS_OID like :C_MDINS_OID and C_RPST_NM in :C_RPST_NM", new String[] {"C_MDINS_OID", "C_RPST_NM"}, new Object[] {"1.2.410%", new String[] {"백운이", "조명구"}})
			);
	}
	
	@SuppressWarnings("rawtypes")
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
		test("save(Object entity)",
				hprovider,
				hdao.insert(hprovider)
			);
		
		Map hproviderMap = new ObjectMapper().convertValue(hprovider, Map.class);
		test("save(Map entityMap)",
				hproviderMap,
				hdao.insert(hproviderMap)
			);
		
	}
	
	public void test_upate() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, JsonProcessingException, IllegalArgumentException, IOException {
		
		/* Test variable */
		ObjectMapper mapper = new ObjectMapper()
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
				.setSerializationInclusion(Include.NON_NULL)
				.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		
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
		
		long id = (long) hdao.insert(mapper.convertValue(hprovider, Map.class));
		
		Map entity = hdao.hquery().where("C_MDINS_ID", id).get();
		
		/* Test cases */
		TB_HPROVIDER update = mapper.convertValue(entity, TB_HPROVIDER.class);
		update.setC_MDINS_NM("5678");
		hdao.update(update);
		test("update(Object entity)",
				entity.get("C_MDINS_NM"),
				update.getC_MDINS_NM(),
				id,
				hdao.hquery().select("C_MDINS_ID, C_MDINS_NM").where("C_MDINS_ID", id).get()
				);
		
		Map updateMap = mapper.convertValue(update, Map.class);
		updateMap.put("C_MDINS_NM", "0000");
		hdao.update(updateMap);
		test("update(Map entityMap)",
				update.getC_MDINS_NM(),
				updateMap.get("C_MDINS_NM"),
				hdao.hquery().select("C_MDINS_ID, C_MDINS_NM").where("C_MDINS_ID", id).get()
				);
	}
	
	public void test_delete() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		/* Test variable */ 
		ObjectMapper mapper = new ObjectMapper()
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
				.setSerializationInclusion(Include.NON_NULL)
				.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
		
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
		long id = (long) hdao.insert(mapper.convertValue(hprovider, Map.class));
		Map entityMap = hdao.hquery().where("C_MDINS_ID", id).get();
		hdao.delete(entityMap);
		test("delete(Map entityMap)",
				id,
				hdao.hquery().where("C_MDINS_ID", id).get()
				);
		
		id = (long) hdao.insert(mapper.convertValue(hprovider, Map.class));
		TB_HPROVIDER entity = mapper.convertValue(hdao.hquery().where("C_MDINS_ID", id).get(), TB_HPROVIDER.class);
		hdao.delete(entity);
		test("delete(Object entity)",
				id,
				hdao.hquery().where("C_MDINS_ID", id).get()
				);
	}
	
	public void test_query_join() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		/* Test variables */
		String paramName = null;
		Object value = null;
		String selectQuery = null;
		String tableQuery = null;
		String whereQuery = null;
		
		/* Test cases */
		
		// 카타시안 프로덕트 (자동 조인)
		selectQuery = toSelectSqlString("tb_code_group.C_GRP_CD,	tb_code_group.C_GRP_CD_NM,	C_COMN_CD,	C_COMN_CD_NM",
										"C_GRP_CD,					C_GRP_CD_NM,				C_COMN_CD,	C_COMN_CD_NM ");
		tableQuery = "from TB_CODE";
		test("query(String queryString)",
				selectQuery,
				tableQuery,
				cdao.query(selectQuery + tableQuery)
			);
		
		// 이너 조인
		selectQuery = toSelectSqlString("a.tb_code_group.C_GRP_CD,	a.tb_code_group.C_GRP_CD_NM,	a.C_COMN_CD,	a.C_COMN_CD_NM",
										"C_GRP_CD,					C_GRP_CD_NM,					C_COMN_CD,		C_COMN_CD_NM ");
		tableQuery = "from TB_CODE a inner join a.tb_code_group ";
		whereQuery = "where a.tb_code_group.C_GRP_CD = :C_GRP_CD";
		paramName = "C_GRP_CD";
		value = "MEDDEPT";
		test("query(String queryString, String paramName, Object value, int ... results)",
				selectQuery,
				tableQuery,
				whereQuery,
				paramName,
				value,
				0,
				5,
				cdao.query(selectQuery + tableQuery + whereQuery, paramName, value, 0, 5)
			);
		
		// 레프트 조인
		tableQuery = "from TB_CODE a left join a.tb_code_group ";
		whereQuery = "where a.tb_code_group.C_GRP_CD = :C_GRP_CD";
		paramName = "C_GRP_CD";
		value = "MEDDEPT";
		test("query(String queryString, String paramName, Object value, int ... results)",
				selectQuery,
				tableQuery,
				whereQuery,
				paramName,
				value,
				0,
				5,
				cdao.query(selectQuery + tableQuery + whereQuery, paramName, value, 0, 5)
			);
		
		// 카타시안 프로덕트
		selectQuery = toSelectSqlString("a.C_MDINS_ID,	a.C_MDINS_NM,	b.C_COMN_CD_NM,		a.C_REG_DTM,	c.C_COMN_CD_NM",
										"C_MDINS_ID,	C_MDINS_NM,		C_MDINS_CLS_NM,		C_REG_DTM,		C_ENTY_STS_NM");
		tableQuery = "from TB_HPROVIDER a, TB_CODE b, TB_CODE c ";
		whereQuery = "where a.C_MDINS_CLS_CD = null and b.C_COMN_CD = a.C_MDINS_CLS_CD and b.tb_code_group.C_GRP_CD = 'HOSTYPE' "
				+ "and c.C_COMN_CD = a.C_ENTY_STS_CD and c.tb_code_group.C_GRP_CD = 'ACTIVETYPE' "
				+ "order by a.C_MDINS_ID desc";
		test("query(String queryString, String paramName, Object value, int ... results)",
				selectQuery,
				tableQuery,
				whereQuery,
				0,
				10,
				cdao.query(selectQuery + tableQuery + whereQuery, 0, 10)
			);
	}
	
public void test_join() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		/* Test variables */
		String paramName = null;
		Object value = null;
		String columnString = null;
		String aliasString = null;
		String whereQuery = null;
		String tableAlias = null;
		String joinColumn = null;
		String joinAlias = null;
		/* Test cases */
		
		// 이너조인
		columnString = 	"b.C_GRP_CD, b.C_GRP_CD_NM, C_COMN_CD, C_COMN_CD_NM";
		aliasString = 	"C_GRP_CD, C_GRP_CD_NM, C_COMN_CD, C_COMN_CD_NM";
		joinColumn = "tb_code_group";
		joinAlias = "b";
		test("hquery().select(String columnString, String aliasString).join(String joinColumn, String alias).list()",
				columnString,
				aliasString,
				joinColumn,
				joinAlias,
				cdao.hquery().select(columnString, aliasString).join(joinColumn, joinAlias).list()
			);
		
		// 이너조인  (with alias)
		tableAlias = "a";
		columnString = 	"b.C_GRP_CD, b.C_GRP_CD_NM, a.C_COMN_CD, a.C_COMN_CD_NM";
		aliasString = 	"C_GRP_CD, C_GRP_CD_NM, C_COMN_CD, C_COMN_CD_NM";
		joinColumn = "tb_code_group";
		joinAlias = "b";
		test("hquery(String alias).select(String columnString, String aliasString).join(String joinColumn, String alias).list()",
				tableAlias,
				columnString,
				aliasString,
				joinColumn,
				joinAlias,
				cdao.hquery(tableAlias).select(columnString, aliasString).join(joinColumn, joinAlias).list()
			);
		
		// 레프트조인  (with alias)
		test("hquery(String alias).select(String columnString, String aliasString).leftJoin(String joiningColumn, String alias).list()",
				tableAlias,
				columnString,
				aliasString,
				joinColumn,
				joinAlias,
				cdao.hquery(tableAlias).select(columnString, aliasString).leftJoin(joinColumn, joinAlias).list()
			);
		
		// 라이트조인  (with alias)
		test("hquery(String alias).select(String columnString, String aliasString).rightJoin(String joiningColumn, String alias).list()",
				tableAlias,
				columnString,
				aliasString,
				joinColumn,
				joinAlias,
				cdao.hquery(tableAlias).select(columnString, aliasString).rightJoin(joinColumn, joinAlias).list()
			);
		
		// 풀조인  (with alias)
		test("hquery(String alias).select(String columnString, String aliasString).fullJoin(String joiningColumn, String alias).list()",
				tableAlias,
				columnString,
				aliasString,
				joinColumn,
				joinAlias,
				cdao.hquery(tableAlias).select(columnString, aliasString).fullJoin(joinColumn, joinAlias).list()
			);
}
	
	public void test_sub_query() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		/* Test variables */
		String selectQuery = null;
		String tableQuery = null;
		
		/* Test cases */
		selectQuery = toSelectSqlString("a.C_MDINS_ID, 	a.C_MDINS_NM,	(select C_COMN_CD_NM from TB_CODE where C_COMN_CD = a.C_MDINS_CLS_CD and C_GRP_CD = 'HOSTYPE'), 	a.C_REG_DTM, 	(select C_COMN_CD_NM from TB_CODE where C_COMN_CD = a.C_ENTY_STS_CD and C_GRP_CD = 'ACTIVETYPE')",
										"C_MDINS_ID, 	C_MDINS_NM, 	C_MDINS_CLS_NM, 																					C_REG_DTM, 		C_ENTY_STS_NM");
		tableQuery = "from TB_HPROVIDER a order by a.C_MDINS_ID desc";
		test("query(String queryString, String paramName, Object value, int ... results)",
				selectQuery,
				tableQuery,
				cdao.query(selectQuery + tableQuery, 0, 10)
			);
		
		test("query(String queryString, String paramName, Object value, int ... results)",
				hdao.hquery("a").select("a.C_MDINS_ID, a.C_MDINS_NM, a.C_REG_DTM", "C_MDINS_ID, C_MDINS_NM, C_REG_DTM")
				.selectSql("select C_COMN_CD_NM from TB_CODE where C_COMN_CD = C_MDINS_CLS_CD and C_GRP_CD = 'HOSTYPE'", "C_MDINS_CLS_NM")
				.selectSql("select C_COMN_CD_NM from TB_CODE where C_COMN_CD = C_ENTY_STS_CD and C_GRP_CD = 'ACTIVETYPE'", "C_ENTY_STS_NM")
				.orderDesc("C_MDINS_ID")
				.list(0, 10)
			);
		
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, JsonProcessingException, IllegalArgumentException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:test/DaoTest.xml"
		});
		
		DaoTest daoTest = context.getBean("daoTest", DaoTest.class);
		
//		daoTest.test_insert();
//		daoTest.test_get();
//		daoTest.test_list();
		daoTest.test_query();
//		daoTest.test_query_join();
//		daoTest.test_join();
//		daoTest.test_upate();
//		daoTest.test_delete();
//		daoTest.test_sub_query();
	}
}
