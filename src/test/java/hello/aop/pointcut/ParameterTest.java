package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {
    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {} ", memberService.getClass());
        memberService.hello("helloA");

    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {
        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];   //조인포인트를 이용하여 파라미터 배열에서 첫번째 것을 가져옴
            log.info("[logArgs1]{}, arg = {}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)") //args()는 객체에 들어가는 파라미터를 가져올 수 있다.
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs2]{}, arg = {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..) ")
        public void logArgs3(String arg) {      //단순히 메서드에 매개변수 이름과 타입을 맞춰서 파라미터를 받을 수 있음
            log.info("[logArgs3] arg = {}", arg);
        }

        @Before("allMember() && this(obj)") //this는 spring container에
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {    //joinpoint 생략 가능
            log.info("[this]{}, obj={}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)")   //target은 실제 객체
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {    //joinpoint 생략 가능
            log.info("[target]{}, obj={}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")   //target은 실제 객체
        public void atTargetArgs(JoinPoint joinPoint, ClassAop annotation) {    //joinpoint 생략 가능
            log.info("[@target]{}, obj={}",joinPoint.getSignature(), annotation.getClass());
        }
        @Before("allMember() && @within(annotation)")   //target은 실제 객체
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {    //joinpoint 생략 가능
            log.info("[@within]{}, obj={}",joinPoint.getSignature(), annotation.getClass());
        }
        @Before("allMember() && @annotation(annotation)")   //target은 실제 객체
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {    //joinpoint 생략 가능
            log.info("[@annotation]{}, annotationValue={}",joinPoint.getSignature(), annotation.value());
        }
    }
}
