package liu.chi.datasources.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractRoutingDataSource的成员
 * Map<Object, Object> targetDataSources中存放读写数据源,应在初始化时设置
 * determineCurrentLookupKey的返回值应该是targetDataSources的key
 *
 * @author liuchi
 * @date 2018-09-23 15:02
 */
public class MultiDataSource extends AbstractRoutingDataSource {

    public MultiDataSource(Map<String, DataSource> targetDataSources, DataSource defaultTargetDataSource) {
        Map<Object, Object> map = new HashMap<>();
        map.putAll(targetDataSources);


        setTargetDataSources(map);
        setDefaultTargetDataSource(defaultTargetDataSource);
    }

    @Override
    protected String determineCurrentLookupKey() {
        return DataSourceConfig.getKey();
    }
}
