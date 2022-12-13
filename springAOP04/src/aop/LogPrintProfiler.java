package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// Aspect Class
//공통 기능을 제공할 클래스 - before , after , around 출력
@Component
@Aspect
public class LogPrintProfiler {
	
	@Pointcut("execution(public * aop..*.*(*,*))")
	public void profileTarget() {}
	
	// 1. Around Advice - 처리 시간 로그로 출력
	@Around("profileTarget()")
	public Object trace( ProceedingJoinPoint joinPoint) throws Throwable{
		
		String signatureString = joinPoint.getSignature().toShortString();
		System.out.println(">" + signatureString + "시작");
		long start = System.nanoTime();
		try {
		Object result = joinPoint.proceed();
		return result;
		}finally {
			long end = System.nanoTime();
			System.out.println(">" + signatureString + "종료");
			System.out.println("> " + signatureString +"처리시간"+(end-start)+"ns");
		}
		
	}//trace
	
	
	//2.Before Advice
	
	//public void before() {}
	@Before("profileTarget()")
	public void before(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("> before advice: " + methodName +"() 호출됨");
		
		
	}
	
	
	//3.After Advice
	@After("profileTarget()")
	public void afterFinally(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("> after advice: " + methodName +"() 호출됨");
	}

}
