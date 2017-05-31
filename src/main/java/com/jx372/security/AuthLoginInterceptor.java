package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;


public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
	//	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()); 
		
		/*컨테이너를 가지고 옴*/
	//	UserService userService = ac.getBean(UserService.class);
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		UserVo userVo = userService.getUser(email, pwd);
		
		if(userVo == null){
			response.sendRedirect(request.getContextPath() + "/user/login?result=fail");
			return false;
		}
		
		//로그인 성공 로그인 처리		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", userVo);
		response.sendRedirect(request.getContextPath() + "/main");
		
		return false;
	}

		
}
