package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//Controllerクラスの各メソッド実行開始・終了時にログを出力
//Apring AOP：JoinPoint（Advice実行タイミング）はAround
@Aspect
@Component
public class LogAspect {

	@Around("execution(* *..*.*Controller.*(..))")
	public Object startendLog(ProceedingJoinPoint jp) throws Throwable {

		System.out.println("メソッド開始: " + jp.getSignature());

		try {

			// 対象クラスのメソッドがここで実行される。
			Object result = jp.proceed();

			System.out.println("メソッド終了: " + jp.getSignature());
			return result;

		} catch (Exception e) {
			System.out.println("メソッド異常終了: " + jp.getSignature());
			e.printStackTrace();
			throw e;

		}
	}
}