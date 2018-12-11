package liu.chi.app.gateway.event.spring;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * 必须在spring.factory中注册,因为component还未被扫描
 */
public class Starting implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("StartingEvent*****");
    }

}
