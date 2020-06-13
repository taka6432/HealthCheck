package com.example.demo.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

//DB関連のエラー発生時のログ
@Aspect
@Component
public class ErrorAspect {

	@AfterThrowing(value = "execution(* *..*..*(..))"
			+ " && (bean(*Controller) || bean(*Service) || bean(*Repository))", throwing = "ex")
	public void throwingNull(DataAccessException ex) {

		System.out.println("======================================================");
		System.out.println("DataAccessExceptionが発生しました。 :" + ex);
		System.out.println("======================================================");

	}

}