package springDI;

import di.RecordImpl;
import di.RecordViewImpl;

public class Ex01 {
	
	public static void main(String[] args) {
		// 1. 스프링DI
		
		
		//인터페이스 di.Record.java	- 총점 ,평균 메서드
		//클래스 	di.RecordImpl.java - 국,영,수 필드
		//인터페이스 di.RecordView.java - 성적 정보를 입력,출력
		//클래스  di.RecordViewImpl.java -
		
		
		RecordImpl record = new RecordImpl();		//A
		
		//RecordViewImpl service = new RecordViewImpl(record);	//B <-DI( 의존성 주입 ) A
		// service.setRecord(record); //setter를 통한 의존성 주입
		RecordViewImpl service = new RecordViewImpl(record); // 생성자를 통한 의존성 주입
		
		service.input();
		service.output();
		
		System.out.println("=END=");

	}//main

}
