package liu.chi.app.gateway.event.spring;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 必须在spring.factory中注册,因为component还未被扫描
 */
public class Failed implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        System.out.println("FailedEvent******");
    }

}
