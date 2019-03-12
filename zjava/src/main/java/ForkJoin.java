import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author liuchi
 * @date 2019-03-12 13:17
 */
public class ForkJoin {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        printTask();
//        sumTask();
    }

    public static void printTask() throws InterruptedException {
        PrintTask task = new PrintTask(0, 300);
        ForkJoinPool pool =new ForkJoinPool(2);
        pool.submit(task);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(20, TimeUnit.SECONDS);
        pool.shutdown();
    }

    public static void sumTask() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        int sum =0;
        for (int i = 0; i < 100; i++) {
            int i1 = random.nextInt(5000);
            list.add(i1);
            sum += i1;
        }
        System.out.println(sum);

        SumTask task = new SumTask(list);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Future<Integer> future = pool.submit(task); //提交分解的SumTask 任务
        System.out.println("多线程执行结果：" + future.get());

        pool.shutdown(); //关闭线程池
    }
}

/**
 * 无返回值
 */
class PrintTask extends RecursiveAction {

    //最多只能打印50个数
    private static final int THRESHOLD = 50;
    private int start;
    private int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {

        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值：" + i);
            }
        } else {
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //并行执行两个“小任务”
            left.fork();
            right.fork();
        }
    }
}


/**
 * 有返回值
 */
class SumTask extends RecursiveTask<Integer> {
    private List<Integer> arry;

    public SumTask(List<Integer> arry) {
        this.arry = arry;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //只处理20个以内的加法
        if (arry.size() < 20) {
            for (int i : arry) {
                sum = sum + i;
            }
            return sum;
        } else {
            //超过20，递归处理
            int middle = arry.size() / 2;
            SumTask left = new SumTask(arry.subList(0, middle));
            SumTask right = new SumTask(arry.subList(middle, arry.size()));
            //并行执行两个小任务
            left.fork();
            right.fork();
            //挂起当前任务，等待小任务执行
            return left.join() + right.join();
        }
    }
}
