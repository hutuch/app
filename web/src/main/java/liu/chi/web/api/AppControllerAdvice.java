package liu.chi.web.api;

import liu.chi.web.pojo.AppException;
import liu.chi.web.pojo.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author：liuchi
 * @date： 2018/02/01 22:07
 */
@ControllerAdvice
public class AppControllerAdvice {

    /**
     * json 串
     */
    @ResponseBody
    @ExceptionHandler(AppException.class)
    public Map<String, Object> handle(AppException e) {
        System.out.println("AppException 捕获控制器异常");
        Map map = new HashMap();
        map.put("code", e.getError().getCode());
        map.put("msg", e.getError().getMsg());
        return map;
    }

    /**
     * 返回视图
     */
    @ExceptionHandler(value = MyException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
        System.out.println("MyException 捕获控制器异常");
        ModelAndView mav = new ModelAndView();
        System.out.println(e.getMessage());
        mav.addObject("detail", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        //名字不能叫error
        mav.setViewName("error2");
        return mav;
    }

}
