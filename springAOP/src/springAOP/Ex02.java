package springAOP;

import aop.CalculatorImple;

public class Ex02 {

	public static void main(String[] args) {
		// 공통기능 - 산술연산 할때 처리 시간을 로그로 기록하고 싶다.
		// 핵심기능 - 덧셈 ~ 나눗셈
		
		CalculatorImple calc = new CalculatorImple();
		
		System.out.println( calc.add(4,2) );
		System.out.println( calc.sub(4,2) );
		System.out.println( calc.mult(4,2) );
		System.out.println( calc.div(4,2) );
		
		System.out.println("=END=");

	}

}
