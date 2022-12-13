package springAOP;

public class Ex01 {

	public static void main(String[] args) {
		// p204 Chapter 06 스프링 AOP + spring MVC
		// 공통관심사항 ( cross-cutting concern) + 핵심관심사항 (core concern)
		//       로깅(인증),보안,트랜잭션 처리
		//	filter( 필터 )
		//	관점 지향적 프로그래밍 ( Spring AOP )
		//[ 인증(로깅) 공통 관심사항 모듈 ]    삽입       글삭제 - 핵심관심사항
		//									   삽입		  글수정 - 핵심관심사항
		//				중복 코딩 제거
		
		
		//***** 반드시 알아야 할 AOP 용어 *****
		// 1. Aspect : 여러객체에 공통으로 적용되는 기능 (공통 기능) 예 - 로깅, 보안, 트랜잭션
		// 2. Advice : [언제] 공통기능을 핵심기능에 적용할지를 정의한다. - 전,후, 전+후 등등
		// 3. Weaving: Advice를 핵심로직코드에 정용(삽입)하는 것.
		// 4. Joinpoint : Advice를 적용(삽입) 가능한 지점 예- 메서드호출시, 필드값 변경시
		// 5. Pointcut : Advice를 실제 적용(삽입)한 지점(Joinpoint)
		//		스프링에서는 정규표현식, [ AspectJ문법 ]을 이용해서 Pointcut을 설정할 수 있다 
		
		//스프링에서 AOP - 프록시 기반 AOP, Joinpoint는 메서드만 사용 가능
		// 스프링에서 3가지 AOP 적용방식
		//	*** XML 파일 		-1. XML 스키마 기반의 POJO 클래스를 이용한 AOP 구현
		//	*** @애노테이션 	-2. AspectJ 에서 정의한 @Aspect 애노테이션 기반의 AOP 구현
		//	개발자가 직접 API 	-3. 스프링 API를 이용한 AOP 구현
		
		//[ 스프링에서 구현 가능한 Advice 종류 ]
		//1. Before Advice
		//2. After Advice : After Throwing Advice,After Returning Advice
		//3. Around Advice
		
		
		
		// [ AOP 예제]
		//[ springAOP 프로젝트 ]
		// ㄱ. aop.Calculator 인터페이스
		// ㄴ. aop.CalculatorImple 클래스 선언
		// ㄷ. Ex02.java + main(){}
		
		
		
		
		

	}//main

}//class
