package liu.chi.web.api;

import liu.chi.web.pojo.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuchi
 * @date 2018-09-21 13:24
 */
@Controller
public class AppMvcController {

    /**
     * MVC控制器
     * modelMap: request.setparameter("","");
     * 返回的字符串解析为view
     */
    @RequestMapping("/thymeleaf")
    public String index(ModelMap map) {
        System.out.println("进入视图控制器");
        map.addAttribute("msg", "MVC Controller");
        return "main";
    }

    @RequestMapping("myexception")
    public String myException() {
        throw new MyException("exception controller");
    }

}
