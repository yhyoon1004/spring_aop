package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {
    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {}  //이 함수를 포인트컷 시그니쳐라고 함   pointcut signature

    @Around("allOrder()")  //위의 포인트 컷 시그니쳐 함수명을 이용해 포인트컷을 추가해 줄 수 있음
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {   //어드바이스
        log.info("[log] {}",joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
