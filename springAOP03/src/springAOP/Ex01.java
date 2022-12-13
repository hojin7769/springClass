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
		// 1. 스프링 API를 이용해서 AOP 구현
		// ***[2. XML 스키마 기반 AOP 구현 ]*** p209(211)
		//		ㄴ 처리 과정
		//			1) 스프링 AOP를 사용하기 위한 의존 추가한다.
		//				com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
		//			2) 공통 기능을 제공할 클래스 구현한다
		//				aop.advice 패키지를 삭제...
		//				aop.LogPrintProfiler.java 클래스
		//						Around Advice : trace() 구현...
		//			3) XML 설정파일(applicationContext.xml)을 <aop:config>태그를 이용해서 Aspect를 설정한다.
		//				Advice를 어떤 Pointcut에 적용할지를 지정한다.
		
		
		
		

	
		
		
		//
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
		
	}//main

}//class
