package com.jx372.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.AdminService;
import com.jx372.mysite.vo.AdminVo;

@Controller
public class MainController {
	
	@Autowired
	AdminService adminservice;
	
	@RequestMapping({"/","/main"})
	public String index(Model model){
		
		AdminVo adminvo = adminservice.get();
		model.addAttribute("adminvo", adminvo);
		return "main/index"; /*viewresolver로 간단해진 view url*/
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello(){
		return "<h1>안녕하세요</h1>";
	}

}
