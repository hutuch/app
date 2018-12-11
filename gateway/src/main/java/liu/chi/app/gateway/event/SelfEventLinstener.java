package liu.chi.app.gateway.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author liuchi
 * @date 2018-12-11 11:22
 */
@Component
public class SelfEventLinstener implements ApplicationListener<SelfEvent> {
    @Override
    public void onApplicationEvent(SelfEvent selfEvent) {
        System.out.println("自定义event事件触发," + selfEvent.getSource());
    }
}
