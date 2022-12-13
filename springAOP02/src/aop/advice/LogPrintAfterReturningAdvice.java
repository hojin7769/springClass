package aop.advice;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

public class LogPrintAfterReturningAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(
			Object returnValue,     //핵심 기능이 처리한 결과 값을 갖는 매개변수
			Method method,			// add()
			Object[] args,			// 		4,2
			Object target			// 핵심 기능을 처리하는 실제 객체
			) throws Throwable {
		
		String methodName = method.getName();
		//로그 기록하는 객체
		Log log = LogFactory.getLog(this.getClass());
		
		log.info(">>>>" + methodName +"() :AfterReturningAdvice 호출됨 확인....");
		
		
	}

}
