package hello.advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;  // 요청의 총 수행 시간을 구하기 위한 필드
    private String message;    // 시작시 사용한 메시지

}
