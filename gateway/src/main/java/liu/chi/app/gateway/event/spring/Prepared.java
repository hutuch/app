package liu.chi.app.gateway.event.spring;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 必须在spring.factory中注册,因为component还未被扫描
 */
public class Prepared implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        System.out.println("PreparedEvent*******");
    }

}
