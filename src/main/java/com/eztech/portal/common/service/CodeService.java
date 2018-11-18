package com.eztech.portal.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

	@Autowired
	private CodeDao cdao;
	
	@SuppressWarnings({ "rawtypes" })
	public List<Map> list(String C_GRP_CD) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return cdao.hquery()
				.select("C_COMN_CD, C_COMN_CD_NM", "key, value")
				.where("tb_code_group.C_GRP_CD", C_GRP_CD)
				.orderAsc("C_COMN_CD")
				.list();
	}
	
}
