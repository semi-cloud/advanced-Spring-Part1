package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 버전 1의 문제점
 * 1. 메서드 호출의 깊이를 표현 불가능하다(레벨).
 * 2. HTTP 요청 단위로 특정 ID를 남겨, 어떤 HTTP 요청에서 시작된 트랜잭션인지 알 수 있어야 하는데 불가능하다.
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try{
            status = trace.begin("OrderController.request()");    // 컨트롤러 이름 + 메서드 이름
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);    // 예외를 먹어버려 어플리케이션 흐름을 정상으로 변경
            throw e;       // 예외를 꼭 다시 던져주어야 함
        }
    }
}
