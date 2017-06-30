package com.jx372.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.jx372.security.Auth;
import com.jx372.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Log LOG = LogFactory.getLog( com.jx372.mysite.controller.UserController.class );
	
	@Autowired
	private UserService userService;      /*DI*/
	
	public void log(String str){

		LOG.warn( "#"+str+" - warn log" );
		LOG.error( "#"+str+" - error log" );		
	}
	
	@RequestMapping( value="/join",method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo){
		log("join-get");
		return "user/join";
	}
	
	@RequestMapping( value="/join",method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model){
		
		if(result.hasErrors()){
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error: list){
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		log("join-post");
		System.out.println(userVo);
	//	userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		log("login-get");
		return "user/login";		
	}
	
	
	@RequestMapping( value="/joinsuccess")
	public String joinsuccess(){
		log("joinsuccess");
		return "/user/joinsuccess";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(Model model, @AuthUser UserVo authUser){
		log("modify-get");
		/*세션이 수정이 안되면 user의 정보가 바뀌지 않아서 다시 회원정보 수정을 들어가면 값이 안바뀜*/		
		model.addAttribute("authUser", authUser);
		return "user/modify";
	}

	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute UserVo authUser){
		log("modify-post");
		userService.editUser(authUser);			
		return "redirect:/main";		
	}
	
	
	/*@ResponseBody
	@RequestMapping( value="/join",method=RequestMethod.POST)
	public String join(@RequestBody String requestBody){
		//System.out.println(userVo);
		return requestBody;
	}*/
	
	
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(
//			HttpSession session,
//			Model model,
//			@RequestParam (value="email", required=true, defaultValue="" ) String email, 
//			@RequestParam (value="pwd", required=true, defaultValue="" ) String pwd){
//		
//		UserVo userVo=userService.getUser(email, pwd);
//		if(userVo==null){
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		
//		session.setAttribute("authUser", userVo);
//		
//		return "redirect:/main";		
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session){
//		
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/main";
//		
//	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handleUserDaoException(){
//		//1.로깅
//		
//		//2.사과 페이지로 안내
//		return "error/exception";
//		
//	}
	
	
	
	

}
