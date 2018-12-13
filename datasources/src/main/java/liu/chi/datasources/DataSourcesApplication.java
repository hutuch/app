package liu.chi.datasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 读写库分离
 *
 * 配置multi datasource,该数据源可根据key选择相应的数据源
 * @author liuchi
 * @date 2018-12-11 17:26
 */
@EnableTransactionManagement(order = 5)
//去除自动配置数据源
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataSourcesApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSourceAutoConfiguration.class, args);
    }
}
