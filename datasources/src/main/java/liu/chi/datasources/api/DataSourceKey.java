package liu.chi.datasources.api;

/**
 * @author liuchi
 * @date 2018-09-23 15:08
 */
public class DataSourceKey {
    public static final String READ = "read";
    public static final String WRITE = "write";

    private static final ThreadLocal<String> keyHolder = new ThreadLocal<>();

    public static String getRoute() {
        return keyHolder.get();
    }

    public static void setRoute(String key) {
        keyHolder.set(key);
    }

    public static void clearRoute() {
        keyHolder.remove();
    }
}
