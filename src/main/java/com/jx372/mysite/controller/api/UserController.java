package com.jx372.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.dto.JSONResult;
import com.jx372.mysite.service.UserService;

@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(
			@RequestParam(value="email", required=true, defaultValue="") String email){
		
		boolean exist = userService.existEamil(email);
		return JSONResult.success(exist);
	}
	

}
