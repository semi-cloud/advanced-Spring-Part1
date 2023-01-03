package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        // 1. 동시성 문제가 발생하지 않는 경우
        sleep(2000);       // A가 완전히 끝나고 나서 B 시작
        // 2. 동시성 문제가 발생하는 경우 : 저장한 데이터와 조회한 데이터가 다른 문제
        sleep(100);        // A가 끝나기 전에 B가 시작되므로, A가 조회하기 전에 B의 값이 저장되어 A 조회 시 B가 출력됌
        threadB.start();

        sleep(3000);     // 메인 쓰레드 종료 대기
        log.info("main exit");

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
