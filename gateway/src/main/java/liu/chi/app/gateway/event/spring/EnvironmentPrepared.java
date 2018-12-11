package liu.chi.app.gateway.event.spring;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 必须在spring.factory中注册,因为component还未被扫描
 */
public class EnvironmentPrepared implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        System.out.println("EnvironmentPreparedEvent******");
    }

}
