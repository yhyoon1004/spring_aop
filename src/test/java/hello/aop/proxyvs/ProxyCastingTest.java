package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {
    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl(); // 구체 클래스도 존재하고 인터페이스도 존재하는 객체
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    //JDK 동적프록시로 생성 (생성자의 인자 생략시애도 JDK)

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        //JDK 동적 프록시를 구현 클래스로 타입 캐스팅시 에러 memberServiceProxy
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }
    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl(); // 구체 클래스도 존재하고 인터페이스도 존재하는 객체
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    //cglib 프록시

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class = {} ",memberServiceProxy.getClass());
        //cglib 프록시를 구현 클래스로 타입 캐스팅 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
