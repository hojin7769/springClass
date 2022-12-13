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
		
		
		//*** p.115(117) 컴포넌트 스캔을 이용한 빈 자동 등록 ***
		
		//@Component
		//@Autowired
		// <context:component-scan base-package="di" />
		
		String resourceLocations="applicationContext.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);
		
		RecordViewImpl service = ( RecordViewImpl )ctx.getBean("service");
		
		
		service.input();
		service.output();
		
		
		ctx.close(); // 스프링 컨테이너 닫기( 빈 제거 )
		System.out.println("=END=");
		
		
		
		//p.118 @Component 애노테이션의 용도별로 하위 애노테이션이 있다.
		//1. Component
		//2. Service
		//3. Repository DAO
		//3. Controller [Model]VC handler
		

	}//main

}
