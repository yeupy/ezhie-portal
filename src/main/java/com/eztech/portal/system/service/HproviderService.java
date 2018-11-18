package com.eztech.portal.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.eztech.portal.system.model.TB_HPROVIDER;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hquery.hQueryHelper;
import com.hquery.HibernateMapDaoSupport.hQuery;

@Service
public class HproviderService extends hQueryHelper {

	@Autowired
	private HproviderDao hdao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> list(int ... results) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		hQuery hq = hdao.hquery()
				.select("C_MDINS_ID, C_MDINS_NM, C_REG_DTM")
				.selectSql("select C_COMN_CD_NM from TB_CODE where C_COMN_CD = C_MDINS_CLS_CD and C_GRP_CD = 'HOSTYPE'", "C_MDINS_CLS_NM")
				.selectSql("select C_COMN_CD_NM from TB_CODE where C_COMN_CD = C_ENTY_STS_CD and C_GRP_CD = 'ACTIVETYPE'", "C_ENTY_STS_NM")
				.orderDesc("C_MDINS_ID");
		
		return hq.list(results);
	}
	
	@SuppressWarnings("rawtypes")
	public int count() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Map countMap = hdao.hquery().select(Projections.count("C_MDINS_ID").as("cnt")).get();
		return Integer.parseInt(String.valueOf(countMap.get("cnt")));
	}
	
	@SuppressWarnings("rawtypes")
	public Map get(long id) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return hdao.hquery().where("C_MDINS_ID", id).get();
	}
	
	public long save(TB_HPROVIDER hprovider) {
		return (long) hdao.insert(hprovider) ;
	}
	
	public void delete(long id) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, DataAccessException, JsonProcessingException, IOException {
		hdao.delete(this.get(id));
	}
	
	public long update(TB_HPROVIDER hprovider) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, JsonProcessingException, IOException {
		hdao.update(hprovider);
		return hprovider.getC_MDINS_ID();
	}
}
