package liu.chi.datasources.conf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * order 应该先@EnableTransactionManagement(order = 5)
 * 去除默认的事务配置(exclude = DataSourceAutoConfiguration.class)
 *
 * @author liuchi
 * @date 2018-09-23 15:39
 */
@Aspect
@Configuration
@Order(1)
public class SwitchDatasource {
    @Pointcut("execution(* liu.chi.datasources.service..*.*(..))")
    public void switchDataSourceAop() {
    }

    @Around("switchDataSourceAop()")
    public Object switchDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
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
