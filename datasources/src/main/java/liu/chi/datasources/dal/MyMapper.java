package liu.chi.datasources.dal;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author liuchi
 * @date 2018-09-22 07:38
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
