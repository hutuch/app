package liu.chi.tkmybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuchi
 * @date 2018-09-22 15:35
 */
@RestController
public class TransactionController {
    @Autowired
    private TransactionService service;

    /**
     * 事务生效
     */
    @RequestMapping("/transaction")
    public String transaction() throws InterruptedException {
        service.insert();
        return "完成";
    }

    /**
     *事务不生效(不回滚)
     */
    @RequestMapping("/transaction2")
    public String transaction2() throws InterruptedException {
        service.insert2();
        return "完成";
    }

    /**
     * 事务生效
     */
    @RequestMapping("/transaction3")
    public String transaction3() throws InterruptedException {
        service.insert3();
        return "完成";
    }
}
