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
		// ***[1. 스프링 API를 이용해서 AOP 구현]***
		//
		//
		
		
		//스프링AOP-API
		//  ㄱ. 스프링 AOP 관련 모듈 추가...
		//       com.springsource.org.aopalliance-1.0.0.jar 추가
		//	ㄴ. Around Advice 필요하다.
		//    aop.advice.LogPrintAroundAdvice.java 
		
		
		// 스프링 객체 X : Calculator calc = new CalculatorImple();
		//springDI04 프로젝트 - ㄴapplicationContext.xml
		
		String resourceLocations ="applicationContext.xml";
		AbstractApplicationContext ctx  = new GenericXmlApplicationContext(resourceLocations);
		
		Calculator calc = (Calculator)ctx.getBean("calcProxy");
		
		System.out.println(calc.add(4,2));
		
		System.out.println("=END=");
		
				
		
		// 2. Before Advice
			// ㄴ 핵심 관심 사항(core concern)이 처리되기 [전]에 실행할 공통기능
			// ㄴ aop.advice.LogPrintBeforeAdvice.java 
		
		//3. After Advice
		//		After Returning Advice
		//		After Throwing Advice
		//		ㄴ aop.advice.LogPrintAfterReturningAdvice
		
		

	}//main

}//class
