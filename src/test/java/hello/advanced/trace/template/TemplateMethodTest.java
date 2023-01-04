package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    // 핵심 기능과 부가 기능이 섞여 있는 상태
    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 템플릿 메서드 패턴 적용 1: 코드 중복 제거
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    /**
     * 템플릿 메서드 패턴 적용 2: 익명 내부 클래스로 매번 클래스를 생성하지 않아도 됨
     */
    @Test
    void templateMethodV2() {
       AbstractTemplate template1 = new AbstractTemplate() {  // 임의의 클래스 이름 : TemplateMethodTest$1
           @Override
           protected void call() {
               log.info("비즈니스 로직1 실행");
           }
       };
       template1.execute();

       AbstractTemplate template2 = new AbstractTemplate() {  // 임의의 클래스 이름 : TemplateMethodTest$2
           @Override
           protected void call() {
               log.info("비즈니스 로직2 실행");
           }
       };
       template2.execute();
    }
}
