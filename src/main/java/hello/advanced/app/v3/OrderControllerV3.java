package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    /**
     * 파라미터 추가가 없는 깔끔한 로그 추적기
     *
     * 필드 동기화 방식 문제점
     * => 저장한 데이터와 조회한 데이터가 다른 동시성 문제 발생
     * => 트래픽이 많아진다면 여러 스레드가 동시에 같은 인스턴스의 필드에 접근하게 되고, 동시에 변경하면서 문제가 발생
     * => FieldLogTrace 는 스프링 빈(싱글톤 객체)으로 관리되므로 하나밖에 없어서 동시성 문제 발생할 수 밖에 없음
     */
    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try{
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
