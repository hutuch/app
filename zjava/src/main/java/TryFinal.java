/**
 * @author liuchi
 * @date 2019-03-12 09:26
 */
public class TryFinal {
    public static void main(String[] args) {
        System.out.println(getInt());
    }

    /**
     * 执行return后面表达式，但不返回值，最终返回的是finally语句
     */
    public static int getInt() {
        int a = 10;
        try {
            return a++;
        } finally {
            System.out.println(a);
            return 5;
        }
    }
}
