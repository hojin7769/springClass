package springDI;

import org.springframework.context.support.GenericXmlApplicationContext;

import di.RecordImpl;
import di.RecordViewImpl;

public class Ex01 {
	
	public static void main(String[] args) {
	
		//*** p105 애노테이션을 이용한[ 의존 자동 주입 ]***
		//1.@Autowired 애노테이션
		//2.@Resouse 애노테이션
		//3.@Inject 애노테이션
		
		String resourceLocations="applicationContext.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);
		
		RecordViewImpl service = ( RecordViewImpl )ctx.getBean("service");
		
		
		service.input();
		service.output();
		
		
		System.out.println("=END=");

	}//main

}
