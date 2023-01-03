package hello.advanced.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {

    private String id;  // HTTP 요청의 Transaction id
    private int level;  // 요청의 깊이

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);  // ID는 같고 깊이만 증가
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);  // ID는 같고 깊이만 감소
    }

    public boolean isFirstLevel() {
        return this.level == 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);  // 앞 8자리만 사용
    }
}

