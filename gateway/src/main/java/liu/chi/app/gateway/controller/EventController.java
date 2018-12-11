package liu.chi.app.gateway.controller;

import liu.chi.app.gateway.event.SelfEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuchi
 * @date 2018-12-11 11:24
 */
@RestController
public class EventController {
    @Autowired
    private AbstractApplicationContext context;

    @RequestMapping("/selfevent")
    public String selfEvent() {
        context.publishEvent(new SelfEvent("自定义event事件发布了"));
        return "已发布";
    }
}
