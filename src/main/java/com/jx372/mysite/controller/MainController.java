package com.jx372.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping({"/","/main"})
	public String index(){
		System.out.println("sss");
		return "main/index"; /*viewresolver로 간단해진 view url*/
	}

}
