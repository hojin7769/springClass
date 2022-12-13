package aop;


import org.springframework.stereotype.Component;

@Component("calc")
public class CalculatorImple implements Calculator {

	// 중복 코딩 발생 -> 제거 해서 -> 공통관심사항 + 핵심 관심 사항 기법( 스프링 AOP )
	
	@Override
	public int add(int x, int y) {
		int result = x+y; //핵시관심 사항( 핵심 기능 )
		return result;
	}

	@Override
	public int sub(int x, int y) {
		int result = x-y; //핵시관심 사항( 핵심 기능 )
		return result;
	}

	@Override
	public int mult(int x, int y) {
		int result = x*y; //핵시관심 사항( 핵심 기능 )
		return result;
	}

	@Override
	public int div(int x, int y) {
		int result = x/y; //핵시관심 사항( 핵심 기능 )
		return result;
	}

}
