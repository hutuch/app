package liu.chi.app.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuchi
 * @date 2018-12-11 10:50
 */
@RestController
public class ShunDownController {
    private static StaticService staticService;

    @Autowired
    private AbstractApplicationContext context;

    @Autowired
    public void setStaticService(StaticService staticService) {
        ShunDownController.staticService = staticService;
    }

    @RequestMapping("/static")
    public String staticService() {
        return staticService.getStr();
    }

    @RequestMapping("/close")
    public String close() {
        context.close();
        return "1";
    }


}
