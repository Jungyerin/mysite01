package com.jx372.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jx372.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		if(supportsParameter(parameter) == false)
		{
			return WebArgumentResolver.UNRESOLVED;  //해석대상이 아니면 
		}
		//@AuthUser가 붙어 있고, 파라미터 타입이 UserVo
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		//@AuthUser에 값을 세팅
		HttpSession session= request.getSession();
		if(session == null){
			return null;
		}
		
		return session.getAttribute("authUser");
	}

	//파라미터 값에 대한 검증(지원하는 타입이냐 아니냐를 검증)
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		//@AuthUser가 안붙어있음
		if(authUser == null){
			
			return false;
		}
		
		//parameter 타입이 uservo가 아님
		if(parameter.getParameterType().equals(UserVo.class) == false){
			
			return false;
		}
		
		return true;
	}

}
