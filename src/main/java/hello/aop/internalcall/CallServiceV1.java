package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired  //setter를 사용하여 자기 자신의 인스턴스 주입 (스프링 2.6 부터 순환참조 금지로 properties 에 옵션 추가해줘야함)
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter = {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal();   //주입받은 빈으로 호출 (외부 메서드 호출)
    }

    public void internal(){
        log.info("call internal");
    }


}
