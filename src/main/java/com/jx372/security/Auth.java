package com.jx372.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) /*타켓을 메소드로 잡았기때문에 클래스에는 쓸 수 없음*/
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
	Role role() default Role.USER;

	public enum Role {ADMIN,USER};
}

	
