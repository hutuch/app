package liu.chi.app.gateway.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 底层使用java的ScheduledExecutorService实现,会包装未捕获异常,若该任务执行时间过长至下一个执行点,则下一执行点不执行
 * <p>
 * ScheduledExecutorService 定时执行的原理是在本任务执行完毕后,计算下次执行时间,并将该任务添加至队列中
 * <p>
 * 表达式生成器 http://cron.qqe2.com/
 * <p>
 * "0 15 10 * * ? *" 每天上午10:15触发
 * "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
 * "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
 * "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
 * "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
 * "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
 */
@EnableScheduling
@EnableAsync
@Component
public class TimeTask {
    private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 默认所有的后台任务都是一个线程执行
     * 不论定时任务被安排在多少个class类中，其依然是单线程执行定时任务（串行任务）
     * 两个task并没有并发执行，而是执行完一个task才会执行另外一个，
     * 而耽误的时间点，不会执行另一个任务。。
     * <p>
     * 解决办法使用异步
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        Thread current = Thread.currentThread();
        Date date = new Date();
        System.out.println("定时任务1  id:" + current.getId() + "; " +
                "name:" + current.getName() + ";" + dateFormat.format(date));
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void execute3() {
        Thread current = Thread.currentThread();
        Date date = new Date();
        System.out.println("定时任务3  id:" + current.getId() + "; " +
                "name:" + current.getName() + ";" + dateFormat.format(date));
    }

    /**
     * 若需要不同业务的定时任务并行任务处理，采用多线程任务。
     * 配置SpringBoot的配置文件：applicationContext.xml，添加如下内容
     * <task:executor id="executor" pool-size="5" />
     * <task:scheduler id="scheduler" pool-size="10" />
     * <task:annotation-driven executor="executor" scheduler="scheduler" />
     */
    @Async
    @Scheduled(cron = "*/300 * * * * ?")
    public void execute2() {
        Date date = new Date();
        Thread current = Thread.currentThread();
        System.out.println("定时任务2  id:" + current.getId() + ";" +
                " name:" + current.getName() + ";" + dateFormat.format(date));
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("timeTask-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}
