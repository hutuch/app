package liu.chi.tkmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author liuchi
 * @date 2018-12-11 17:18
 */
// todo
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)//暴露代理
@MapperScan(basePackages = "liu.chi.app.dal.mapper")
public class TkMybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(TkMybatisApplication.class, args);
    }
}
