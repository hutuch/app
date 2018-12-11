package liu.chi.app.gateway.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义 spring event,通过spring context来发布事件
 *
 * @author liuchi
 * @date 2018-12-11 11:12
 */
@Data
public class SelfEvent extends ApplicationEvent {

    public SelfEvent(Object source) {
        super(source);
    }
}
