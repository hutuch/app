package liu.chi.app.gateway.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author liuchi
 * @date 2018-09-21 17:27
 */
@Service
public class ThreadService {
    @Autowired
    private ThreadPoolExecutor executor;

    public void shutdown() {
        /**
         * 线程池的状态立刻变成STOP状态;
         * 试图停止所有正在执行的线程，不再处理还在池队列中等待的任务;
         * 会返回那些未执行的任务
         */
        executor.shutdownNow();

        /**
         * 当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态;
         * 不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常;
         * 但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出
         */
        executor.shutdown();
    }

    public void runnable() {
        Runnable r = () -> {
            System.out.println(".....");
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
            }
        };
        executor.execute(r);
    }

    public void callable() {
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(5);
            return "call result";
        };

        Future<String> submit = executor.submit(callable);

        try {
            String s = submit.get(2, TimeUnit.SECONDS);
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("2s没取到值");
        }


        try {
            String s1 = submit.get();
            System.out.println(s1);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }

    public void futureTask() {
        FutureTask task = new FutureTask(null);
        executor.execute(task);
    }

}
