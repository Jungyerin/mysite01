package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jx372.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1. handler 종류
		if(handler instanceof HandlerMethod == false){
			return true;
		}
		
		//2. @Auth가 붙어 있는 지 확인 /*인터페이스로 만들어준 @auth 어노테이션을 가지고 와서 구현*/
		Auth auth = ((HandlerMethod)handler).getMethodAnnotation(Auth.class);
				
		//3. @Auth가 붙어 있지 않으면 클래스에서 찾아봐야 함
		if(auth==null){
			//++++4. calss에 붙어있는지 학인?
			auth = ((HandlerMethod)handler).getMethod().getDeclaringClass().getAnnotation(Auth.class);
			if(auth==null){
				return true;
			}
		}
		
			
		//5. 접근 제어
		HttpSession session = request.getSession();
		if(session == null){
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		if( session.getAttribute( "authUser" ) == null ) {
			response.sendRedirect( request.getContextPath() + "/user/login" );
			return false;
		}
		
		//6. 롤체크
		Auth.Role role = auth.role();
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		
		if(role==Auth.Role.ADMIN && authUser.getRole().equals("ADMIN")==false){
			return false;
		}

		return true;
	}
	
	

}
