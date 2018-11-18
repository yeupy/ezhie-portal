package com.eztech.portal.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/portal")
public class MainController {

	// 메인
	@RequestMapping(value = "/Main.do", method = RequestMethod.GET)
	public String Main(Model model) {
		return "portal/common/main";
	}
}
