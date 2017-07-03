package com.jx372.mysite.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jx372.mysite.dto.JSONResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class) /*모든 클래스로부터 받겠다는 의미.*/
	public void handleException(
			HttpServletRequest request, 
			HttpServletResponse response,
			Exception e) throws IOException, ServletException{
		//1. 로깅
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		//2. 요청 종류 알아내기
		String accept = request.getHeader("accept");
		System.out.println(accept);
		
		//3.응답
		if(accept.matches(".*application/json.*") == true){
			//JSON 요청(AJAX request, XMLHttpRequest)
			response.setStatus(HttpServletResponse.SC_OK);
			
			JSONResult jsonResult = JSONResult.error("예외가 발생했습니다.");
			
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			
		}else{
			//WEB 요청(html, image, xml, js, css...)
//			ModelAndView mav = new ModelAndView();
//			mav.addObject("exception", errors.toString());
//			mav.setViewName("error/exception");
//			System.out.println(e);
			request.setAttribute("url", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}

	}	

}
