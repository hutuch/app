package liu.chi.app.gateway.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 推荐是用order标注顺序，当多个启动任务的时候，会阻塞
 */
@Slf4j
@Order(1)
@Configuration
public class StartTask implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("SpringBoot 启动任务(CommandLineRunner),已经执行..");
    }
}
