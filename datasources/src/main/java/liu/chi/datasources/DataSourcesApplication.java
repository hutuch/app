package liu.chi.datasources;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 读写库分离
 * @author liuchi
 * @date 2018-12-11 17:26
 */
//todo
@EnableTransactionManagement(order = 5)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataSourcesApplication {
}
