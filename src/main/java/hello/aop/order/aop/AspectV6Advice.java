package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {
    //hello.app.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint)throws Throwable {
        try {
            //@Before 시점
            log.info("[트랜젝션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed(); 이렇게 joinPoint.proceed()를 호출하지 않을 경우 타겟을 실행하지 않음
            //@AfterReturning 시점
            log.info("[트랜젝션 커밋] {}", joinPoint.getSignature());
//            return result;
            return null;
        } catch (Exception e) {
//            @AfterThrowing 시점
            log.info("[트랜젝션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
//            @After() 시점
            log.info("[리소스 릴리즈] {}",joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {//매개변수를 안넣어줘도 상관없음
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    //returning 이름이 매칭되어 매개변수로 들어옴|| returning의 대상객체의 타입과 일치하는 것만 대상으로 실행.
    public void doAfterReturning(JoinPoint joinPoint, Object result) {  //해당 객체를 다른 객체로 바꿀순 없지만 해당 객체를 조작은 가능
        log.info("[return] {} result = {}", joinPoint.getSignature(), result);
    }
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result2")
    public void doAfterReturning2(JoinPoint joinPoint, String result2) {  //\
        log.info("[return2] {} result = {}", joinPoint.getSignature(), result2);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} meassage = {}", ex);
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }

}
