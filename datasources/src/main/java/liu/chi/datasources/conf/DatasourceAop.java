package liu.chi.datasources.conf;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuchi
 * @date 2018-09-23 15:39
 */
@Slf4j
@Aspect
@Configuration
@Order(1)
//指定配置事务的顺序,目的是先执行数据库的选择
@EnableTransactionManagement(order = 5)
public class DatasourceAop {

    @Pointcut("execution(* liu.chi.datasources.read..*.*(..))")
    public void switchDataSourceAop() {
    }

    @Around("switchDataSourceAop()||@annotation(readOnly)")
    public Object switchDataSource(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable {
        boolean isSwitch = false;
        /**
         * 当前线程为空,表示第一次进入
         */
        if (DataSourceConfig.getKey() == null) {
            isSwitch = true;

            String name = joinPoint.getSignature().getName();
            if (name.startsWith(DataSourceConfig.READ)) {
                DataSourceConfig.setReadDataSource();
            } else {
                DataSourceConfig.setWriteDataSource();
            }
        }

        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        } finally {
            if (isSwitch) {
                DataSourceConfig.removeKey();
            }
        }
    }
}
