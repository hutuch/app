package liu.chi.datasources.conf;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置数据源
 *
 * @author liuchi
 * @date 2018-09-23 15:00
 */
@Configuration
public class DataSourceConfig {
    private static final ThreadLocal<String> key = new ThreadLocal<>();

    public static final String READ = "READ";
    public static final String WRITE = "WRITE";


    public static String getKey() {
        return key.get();
    }

    public static void setReadDataSource() {
        key.set(READ);
    }

    public static void setWriteDataSource() {
        key.set(WRITE);
    }

    public static void removeKey() {
        key.remove();
    }

    /**
     * 配置数据源,该数据源可以根据key 查找响应的数据源
     */
    @Bean
    public DataSource multiDatasource() {
        Map<String, DataSource> map = new HashMap<>();

        DataSource read = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .username("root").password("123456").url("localhost:3306/test").build();
        map.put(READ, read);

        DataSource write = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .username("root").password("123456").url("localhost:3306/test").build();
        map.put(WRITE, write);
        MultiDataSource dataSource = new MultiDataSource(map, write);

        return dataSource;
    }

}
