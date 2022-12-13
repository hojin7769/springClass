package springDI;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import di.Config;
import di.RecordViewImpl;

public class Ex01 {
	
	public static void main(String[] args) {
		//p85(87) 자바 코드를 이용한 DI 설정
		
		//C:\spring-framework-3.0.2.RELEASE-dependencies\net.sourceforge.cglib\com.springsource.net.sf.cglib\2.2.0
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( di.Config.class );
		
		RecordViewImpl service = ( RecordViewImpl )ctx.getBean("service");
		
		
		service.input();
		service.output();
		
		
		
		System.out.println("=END=");

	}//main

}//class


