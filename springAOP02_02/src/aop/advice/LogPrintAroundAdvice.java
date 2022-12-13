package aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

// Advice = 공통관심사항(Aspect) + 적용시점 정의된 것
@Component
public class LogPrintAroundAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		String methodName = method.getMethod().getName();
		// 로그 기록
		Log log = LogFactory.getLog(this.getClass());
		StopWatch sw = new StopWatch();
		log.info(">" + methodName + "() start.");
		sw.start();
		
		//핵심 관심 사항(핵심 기능) + - * /
		Object result =  method.proceed();
		
		sw.stop();
		log.info(">" + methodName + "() stop.");
		log.info(">" + methodName +"() 처리시간:" + sw.getTotalTimeMillis() + "ms");
		return result;
	}

}
