package liu.chi.datasources.api;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源,getConnection可以切换库
 *
 * @author liuchi
 * @date 2018-09-23 15:02
 */
public class MultiDataSource extends AbstractRoutingDataSource {

    private Map<String, DataSource> targetDataSources;

    private DataSource defaultDataSource;

    public MultiDataSource(Map<String, DataSource> targetDataSources, DataSource defaultDataSource) {
        this.targetDataSources = targetDataSources;
        this.defaultDataSource = defaultDataSource;
    }

    /**
     * 获取key
     */
    @Override
    protected String determineCurrentLookupKey() {
        return DataSourceKey.getRoute();
    }

    /**
     * 初始化父类
     */
    @Override
    public void afterPropertiesSet() {
        setTargetDataSources(new HashMap<>(targetDataSources));
        setDefaultTargetDataSource(defaultDataSource);
        //不可少
        super.afterPropertiesSet();
    }
}
