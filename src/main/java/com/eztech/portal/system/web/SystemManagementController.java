package com.eztech.portal.system.web;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.eztech.portal.system.model.TB_HPROVIDER;
import com.eztech.portal.system.service.HproviderService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/portal/system")
public class SystemManagementController {

	@Resource
	HproviderService hproviderService;
	
	@Value("${web.pagination.record-size}")
	private int recordSize;
	
	@Value("${web.pagination.page-size}")
	private int pageSize;
	
	// 의료기관 관리
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getHproviderList.do")
	public String getHproviderList(@RequestParam Map<String, String> params, ModelMap model) throws IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		String pageStr = "page";
		int pageIndex = params.containsKey(pageStr) ? Integer.parseInt(params.get(pageStr)) : 0;
		int recordIndex = recordSize * pageIndex;
				
		/* pagination setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageIndex);
		paginationInfo.setRecordCountPerPage(recordSize);
		paginationInfo.setPageSize(pageSize);
		paginationInfo.setCurrentPageNo(pageIndex);
		
		List<Map> hproviderList = hproviderService.list(recordIndex, recordSize);
		model.addAttribute("hproviderList", hproviderList);

		paginationInfo.setTotalRecordCount(hproviderService.count());
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "portal/system/HproviderList";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getHproviderDetail.do", method = RequestMethod.GET)
	public @ResponseBody Map getHproviderDetail(@RequestParam("id") long id) throws IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		return hproviderService.get(id);
	}
	
	@RequestMapping(value = "/saveHprovider.do", method = RequestMethod.POST)
	public String saveHprovider(@ModelAttribute("hprovider") TB_HPROVIDER hprovider, SessionStatus status, RedirectAttributes redirectAttr) throws IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		hprovider.setC_MDINS_OID("1.2.410.100110.10.10000001");
		hprovider.setC_MDINS_POST_NO("42644");
		hprovider.setC_RCORG_NO("10000001");
//		hprovider.setC_MDINS_CLS_CD("");
//		hprovider.setC_ENTY_STS_CD("");
		hprovider.setC_EMR_SYS_CLS_YN('Y');
		hprovider.setC_DMD_SYS_CLS_YN('N');
		hprovider.setC_REG_DTM(new Date(Calendar.getInstance().getTimeInMillis()));
		hprovider.setC_REG_ID("C0HIE");
		hprovider.setC_MDF_DTM(new Date(Calendar.getInstance().getTimeInMillis()));
		hprovider.setC_MDF_ID("C0HIE");
		hprovider.setC_USE_YN('Y');
		
		// 전화번호 split
		String[] tel = hprovider.getC_TEL_NO_1().split("\\s+");
		int i = 0;
		for(String _tel: tel) {
			if(i == 0) {
				hprovider.setC_TEL_NO_1(_tel);
			} else if(i == 1) {
				hprovider.setC_TEL_NO_2(_tel);
			} else if(i == 2) {
				hprovider.setC_TEL_NO_3(_tel);
			}
			i++;
		}
		
		long id = hproviderService.save(hprovider);
		status.setComplete();
		
		Map<String, Object> postAlert = new HashMap<>();
		postAlert.put("msg", "새 의료기관이 추가되었습니다.");
		postAlert.put("id", id);
		redirectAttr.addFlashAttribute("postAlert", postAlert);
		return "redirect:/portal/system/getHproviderList.do";
	}
	
	@RequestMapping(value = "/deleteHprovider.do", method = RequestMethod.POST)
	public String deleteHprovider(@ModelAttribute("hprovider") TB_HPROVIDER hprovider, @RequestParam("urlParams") String urlParams, SessionStatus status, RedirectAttributes redirectAttr) throws IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		hproviderService.delete(hprovider.getC_MDINS_ID());
		status.setComplete();
		
		Map<String, Object> postAlert = new HashMap<>();
		postAlert.put("msg", "의료기관이 삭제되었습니다.");
		redirectAttr.addFlashAttribute("postAlert", postAlert);
		return "redirect:/portal/system/getHproviderList.do" + urlParams;
	}
	
	@RequestMapping(value = "/updateHprovider.do", method = RequestMethod.POST)
	public String updateHprovider(@ModelAttribute("hprovider") TB_HPROVIDER hprovider, @RequestParam("urlParams") String urlParams, SessionStatus status, RedirectAttributes redirectAttr) throws IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		
		// 전화번호 split
		String[] tel = hprovider.getC_TEL_NO_1().split("\\s+");
		hprovider.setC_TEL_NO_1(tel.length > 0 ? tel[0] : "");
		hprovider.setC_TEL_NO_2(tel.length > 1 ? tel[1] : "");
		hprovider.setC_TEL_NO_3(tel.length > 2 ? tel[2] : "");
		
		long id = hproviderService.update(hprovider);
		status.setComplete();
		
		Map<String, Object> postAlert = new HashMap<>();
		postAlert.put("msg", "의료기관이 수정되었습니다.");
		postAlert.put("id", id);
		redirectAttr.addFlashAttribute("postAlert", postAlert);
		return "redirect:/portal/system/getHproviderList.do" + urlParams;
	}
	
}
