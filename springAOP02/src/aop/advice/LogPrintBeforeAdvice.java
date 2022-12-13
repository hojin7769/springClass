package aop.advice;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class LogPrintBeforeAdvice  implements MethodBeforeAdvice{

	@Override
	public void before(Method method,    //add(4,2)
						Object[] args,   //		4,2
						Object target	 // 실제 핵심 가능을 하는 객체 calc 
						) throws Throwable {
		
		String methodName = method.getName();
		//로그 기록하는 객체
		Log log = LogFactory.getLog(this.getClass());
		
		log.info(">>>>" + methodName +"() :BeforeAdvice 호출됨 확인....");
		
		
		
	}

}
