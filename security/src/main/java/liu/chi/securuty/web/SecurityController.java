package liu.chi.securuty.web;

import liu.chi.securuty.conf.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author liuchi
 * @date 2018-11-08 13:21
 */
@RestController
public class SecurityController {

    @RequestMapping("getchi")
    public Stu getChi(Stu stu) {
        //获取当前角色
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            System.out.println("当前用户名:"+user.getUsername());
            System.out.println("当前密码:"+user.getPassword());
            System.out.println("当前角色:"+user.getRole());
        }

        System.out.println(stu);

        Stu s = new Stu();
        s.setName("security-provider");
        s.setAge(25);
        s.setBirthday(LocalDateTime.now());
        s.setAssets(new BigDecimal("125.042"));
        return s;
    }
}
