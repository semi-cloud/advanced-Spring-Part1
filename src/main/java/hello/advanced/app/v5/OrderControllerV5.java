package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 템플릿 콜백 패턴(+ 전략 패턴) 적용
 *
 * 장점 : 상속이 아닌 인터페이스를 사용해 단점 제거 및 콜백 패턴으로 유연성 증가
 * 단점 : 여전히 로그 추적기와 비즈니스 핵심 로직이 섞여 있음
 *
 * 추가 : TraceTemplate은 의존성 주입말고도 빈으로 한번에 등록할 수 있지만 테스트가 힘들어짐
 * 컨트롤러, 서비스, 레포지토리는 싱글톤이기 때문에 매 요청마다 생성하는 것이 아닌 총 3번만 생성하는데, 객체 생성 비용은 상관 X
 */
@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return traceTemplate.execute("OrderController.request()", new TraceCallback<String>() {
               @Override
               public String call() {
                    orderService.orderItem(itemId);
                    return "ok";
                }
        });
    }
}
