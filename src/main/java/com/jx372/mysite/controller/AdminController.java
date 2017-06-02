package com.jx372.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.service.AdminService;
import com.jx372.mysite.vo.AdminVo;
import com.jx372.security.Auth;

@Auth(role=Auth.Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminservice;
	
	@RequestMapping("/main")
	public String main(Model model){
		
		AdminVo adminvo = adminservice.get();
		model.addAttribute("adminvo", adminvo);
		System.out.println("main adminvo : "+adminvo);
		return "admin/main";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String write(Model model, 
			@ModelAttribute AdminVo adminvo) {
		System.out.println("insert : "+adminvo);
		adminservice.insert(adminvo);		
		return "redirect:/admin/main";
	}
		
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute AdminVo adminvo,
			@RequestParam("file1") MultipartFile file1){
	
		String url1 = adminservice.restore(file1);
	
		adminvo.setFile(url1);
		System.out.println("update : "+adminvo);
		adminservice.update(adminvo);

		return "redirect:/admin/main";
	}
	
	
	@RequestMapping("/guestbook")
	public String guestbook(){
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board(){
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user(){
		return "admin/user";
	}

}
