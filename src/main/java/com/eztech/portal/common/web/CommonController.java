package com.eztech.portal.common.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eztech.portal.common.service.CodeService;

@Controller
@RequestMapping("/portal/com")
public class CommonController {

	@Resource
	CodeService codeService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/codes.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getCode(@RequestParam("id") String groupCode) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return codeService.list(groupCode);
	}
}
