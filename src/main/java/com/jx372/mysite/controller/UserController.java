package com.jx372.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.exception.UserDaoException;
import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;      /*DI*/
	
	@RequestMapping( value="/join",method=RequestMethod.GET)
	public String join(){
		return "user/join";
	}
	
/*	@RequestMapping( value="/join",method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo){
		//System.out.println(userVo);
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}*/
	
	@ResponseBody
	@RequestMapping( value="/join",method=RequestMethod.POST)
	public String join(@RequestBody String requestBody){
		//System.out.println(userVo);
		return requestBody;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		return "user/login";		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			HttpSession session,
			Model model,
			@RequestParam (value="email", required=true, defaultValue="" ) String email, 
			@RequestParam (value="pwd", required=true, defaultValue="" ) String pwd){
		
		UserVo userVo=userService.getUser(email, pwd);
		if(userVo==null){
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		session.setAttribute("authUser", userVo);
		
		return "redirect:/main";		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
		
	}
	
	@RequestMapping( value="/joinsuccess")
	public String joinsuccess(){
		return "/user/joinsuccess";
	}
	
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(HttpSession session){
		
		UserVo authUser=(UserVo) session.getAttribute("authUser");	/*세션이 수정이 안되면 user의 정보가 바뀌지 않아서 다시 회원정보 수정을 들어가면 값이 안바뀜*/		
		
		if(authUser==null){
			return "redirect:/user/login";
		}

		return "user/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(
			@ModelAttribute UserVo authUser){

		//System.out.println(authUser+" "+no);
		userService.editUser(authUser);	
		
		return "redirect:/main";		
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handleUserDaoException(){
//		//1.로깅
//		
//		//2.사과 페이지로 안내
//		return "error/exception";
//		
//	}
	
	
	
	

}
