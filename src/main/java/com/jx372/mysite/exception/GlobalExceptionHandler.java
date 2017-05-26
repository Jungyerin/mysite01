package com.jx372.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class) /*모든 클래스로부터 받겠다는 의미.*/
	public ModelAndView handleException(Exception e){
		//1. 로깅
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		//2. 안내페이지 가기
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", errors.toString());
		mav.setViewName("error/exception");
		System.out.println(e);
		
		return mav;
	}
	
	

}
