package springAOP;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

import aop.Calculator;
import aop.CalculatorImple;

public class Ex01 {

	public static void main(String[] args) {
		// p204 Chapter 06 스프링 AOP + spring MVC
		
		//스프링 AOP 3가지...
		// 1. 스프링 API를 이용해서 AOP 구현 springAOP02_02
		// 2. XML 스키마 기반 AOP 구현 springAOP03
		// 3.*** [3.@Aspect 애노테이션으로 선언된 Aspect 클래스  ]springAOP04
		//		ㄴ 처리 과정
		//			1) @Aspect 애노테이션을 이용해서 [Aspect 클래스 = LogPrintProfiler] 구현한다.
		//				 Aspect 클래스는 Advice를 구현한 메서드와 Pointcut을 포함한다
		//			2) XML 설정(applicationContext.xml) 에서 <aop:aspectj-autoproxy>를 설정한다
		//				java파일 기반이라면 @EnableAspectJProxy 애노테이션을 설정한다
		
		
		

		
		String resourceLocations ="applicationContext.xml";
		AbstractApplicationContext ctx  = new GenericXmlApplicationContext(resourceLocations);
		
		//Calculator calc = (Calculator)ctx.getBean("calcProxy");
		Calculator calc = (Calculator)ctx.getBean("calc");
		System.out.println(calc.add(4,2));
		
		System.out.println("=END=");
		
		
		
		
		
		
		
		/*
		>Calculator.add(..)시작
		> before advice: add() 호출됨
		>Calculator.add(..)종료
		> Calculator.add(..)처리시간131800ns
		6
		=END=
		*/
		
		
		//[spring MVC]
		//spring-tool-suite-3.9.13.RELEASE-e4.16.0-win32-x86_64.zip
		//STS
		
	}//main

}//class
