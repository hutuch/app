package liu.chi.datasources.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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

    public static final String READ = "read";
    public static final String WRITE = "write";

    /**
     * 配置数据源,该数据源可以根据key 查找响应的数据源
     */
    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> map = new HashMap<>();

        DataSource read = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .username("read").password("123456").url("localhost:3306/app").build();
        map.put(READ, read);

        DataSource write = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .username("none").password("123456").url("localhost:3306/app").build();

        map.put(WRITE, write);
        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(map);
        dataSource.setDefaultTargetDataSource(write);

        return dataSource;
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }

    /**
     * 多数据源需要自己设置sqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        bean.setDataSource(routingDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage("liu.chi.datasources.dal.mapper");
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

//        bean.setConfigLocation(resolver.getResource(""));
        return bean.getObject();
    }


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

}
