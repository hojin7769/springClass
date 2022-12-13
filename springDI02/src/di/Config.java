package di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//applicationContext.xml 파일 처럼 [ 자바설정파일] 이다.
@Configuration
public class Config {
	
	//<bean></bean>
	
	@Bean
	public RecordImpl record() {
		return new RecordImpl();
	}
	
	
	@Bean(name = "service")
	public RecordViewImpl getRecordViewImpl() {
		RecordViewImpl service = new RecordViewImpl();
		service.setRecord( record() );
		
		return service;
	}
	
	

}
