package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    @Around("execution(* hello.aop.order..*(..))")  //포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {   //어드바이스
        log.info("[log] {}",joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
