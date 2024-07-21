package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {
//어플리케이션 컨텍스트에서 직접 꺼내기엔 조금 부담스러움
//    private final ApplicationContext applicationContext;
//    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
    //ObjectProvider 로 지연 로딩
    //ObjectProvider 는 스프링의 applicationContext에서 bean을 기져오는 시점을 생성시점에서 객체 사용 시점으로 바꿔줌
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }

    public void external() {
        log.info("call external");
        //        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getObject();
        callServiceV2.internal();
    }

    public void internal(){
        log.info("call internal");
    }


}
