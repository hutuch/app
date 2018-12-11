package liu.chi.app.gateway.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author liuchi
 * @date 2018-12-11 13:34
 */
@Component
public class Bean {

    @Value("${system.start}")
    private String start;

    @Value("${system.close}")
    private String end;

    @PostConstruct
    public void start() {
        System.out.println("@PostConstruct已执行");
        System.out.println("${system.start}已被spring注入,值为:" + start);
        System.out.println("已添加shut down hook");

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> System.out.println("shut down hook 任务已执行,完毕")));

    }


    /**
     * 在cotext.close()调用后执行
     */
    @PreDestroy
    public void close() {
        System.out.println("@PreDestroy已执行");
        System.out.println("${system.end}已被spring注入,值为:" + end);
    }
}
