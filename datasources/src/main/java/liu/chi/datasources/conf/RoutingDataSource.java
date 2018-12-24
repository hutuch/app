package liu.chi.datasources.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource的成员
 * Map<Object, Object> targetDataSources中存放读写数据源,应在初始化时设置
 * determineCurrentLookupKey的返回值应该是targetDataSources的key
 *
 * @author liuchi
 * @date 2018-09-23 15:02
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected String determineCurrentLookupKey() {
        //根据该key去map中查找
        return DataSourceConfig.getKey();
    }
}
