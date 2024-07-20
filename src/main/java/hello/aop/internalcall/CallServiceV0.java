package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    public void external() {
        log.info("call external");
        internal();//내부 메서드 호출 (this.internal()) => aop가 적용되지 않음
        //why? -> 위의 코드는 this.internal() 즉, 자기자신의 인스턴스 메서드를 호출한 것이기 때문
    }

    public void internal(){
        log.info("call internal");
    }


}
