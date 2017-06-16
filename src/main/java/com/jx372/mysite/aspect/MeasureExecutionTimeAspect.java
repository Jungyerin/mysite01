package com.jx372.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect{
	
	
	@Around("execution( * *..repository.*.*(..)) || execution( * *..service.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
		//before advice
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		//method 실행
		Object result = pjp.proceed();
		
		//after advice
		stopWatch.stop();
		Long totalTime = stopWatch.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "."+methodName;
		
		System.out.println("[ExecutionTime]["+taskName+"] "+totalTime+"millis");
		return result;
	}

}