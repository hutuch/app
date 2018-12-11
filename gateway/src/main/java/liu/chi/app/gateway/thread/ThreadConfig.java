package liu.chi.app.gateway.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.线程池在创建的时候线程数为0
 * <p>
 * 2.调用ThreadPoolExecutor的execute提交线程，首先检查CorePool，如果CorePool内的线程小于CorePoolSize，新创建线程执行任务。
 * <p>
 * 3.如果当前CorePool内的线程大于等于CorePoolSize，那么将线程加入到BlockingQueue。
 * <p>
 * 4.如果不能加入BlockingQueue，在小于MaxPoolSize的情况下创建线程执行任务。
 * <p>
 * 5.如果线程数大于等于MaxPoolSize，那么执行拒绝策略。
 * <p>
 * 6、当一个线程无事可做，超过一定的时间（keepAliveTime）时，线程池会判断，如果当前运行的线程数大于 corePoolSize，
 * 那么这个线程就被停掉,所以线程池的所有任务完成后，它最终会收缩到 corePoolSize 的大小。
 *
 * @author liuchi
 * @date 2018-09-21 16:07
 */
@Configuration
public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {

        int corePoolSize = 3;
        int maximumPoolSize = 10;
        int keepAliveTime = 5 * 60;
        TimeUnit unit = TimeUnit.SECONDS;

        /**
         * 任务队列(先进先出,先进后出,优先级队列)
         * LinkedBlockingQueue:基于链表的队列,FIFO(先进先出)
         */
        BlockingQueue queue = new LinkedBlockingQueue(1024);

        /**
         * 用于设置创建线程的工厂，
         * 可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
         */
        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("ThreadPoolExecutor" + poolNumber.incrementAndGet());
                return thread;
            }
        };

        /**
         * 饱和策略
         * 当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。
         * 这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常
         */
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            private final Logger logger = LoggerFactory.getLogger(RejectedExecutionHandler.class);

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.error("丢弃任务");
            }
        };


        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue, factory, handler);
        return executor;
    }
}
