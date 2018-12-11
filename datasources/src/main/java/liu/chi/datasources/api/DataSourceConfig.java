package liu.chi.datasources.api;

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
    /**
     * 配置数据源,该数据源可以根据key 查找响应的数据源
     */
    @Bean
    public DataSource multiDatasource() {
        Map<String, DataSource> map = new HashMap<>();
        map.put(DataSourceKey.READ, readDataSource());
        map.put(DataSourceKey.WRITE, writeDataSource());
        MultiDataSource dataSource = new MultiDataSource(map, readDataSource());
        return dataSource;
    }


    /**
     * 初始化读库
     */
    public DataSource readDataSource() {
        return null;
    }

    /**
     * 初始化写库
     */
    public DataSource writeDataSource() {
        return null;
    }

}
