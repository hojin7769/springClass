package springDI;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

import di.RecordImpl;
import di.RecordViewImpl;

public class Ex02 {
	
	public static void main(String[] args) {
		
		
		/*
		 객체생성
		 의존관계가 있는 객체를 주입.
		RecordImpl record = new RecordImpl();
		RecordViewImpl service = new RecordViewImpl(record);
		
		service.input();
		service.output();
		*/
		
		
		 //스프링 프레임워크 사용해서 스프링 컨테이너에서   스프링 빈 객체 생성 + 주입
		
		//1. 스프링 프레임워크(스프링 주요 모듈들) 프로젝트에 추가.
		
		//Chapter02 정독..
		//스프링 빈
		//스프링 컨테이너 == DI 컨테이너  == Ioc 컨테이너
		//2. 설정 파일 작성
		//	ㄱ. xml 파일 - applicationContext.xml
		//	ㄴ. 자바 파일
		
		
		//C:\spring-framework-3.0.2.RELEASE-with-docs\spring-framework-3.0.2.RELEASE\docs\spring-framework-reference\htmlsingle
		
		
		
		String resourceLocations="applicationContext.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);
		
		//ctx 스프링 컨테이너에서 ctx 공장에서 service 이름의 빈 객체를 얻어와야 한다.
		RecordViewImpl service = ( RecordViewImpl )ctx.getBean("service");
		
		
		service.input();
		service.output();
		
		System.out.println("=END=");

	}//main

}
