package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 파라미터 동기화 방식의 장점
 1. HTTP 요청을 구분하고 깊이를 표현 가능

 파라미터 동기화 방식의 문제점
 1. TraceId의 동기화를 위해 관련 메서드(or 인터페이스)의 모든 파라미터 수정 필요
 2. 로그를 처음 시작할때는 begin(), 처음이 아닐 때는 beginSync() 호출 필요
 3. 컨트롤러가 아니라 서비스부터 호출하는 상황이라면 파라미터로 넘길 traceId가 존재하지 X
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try{
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
