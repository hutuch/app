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
    @RequestMapping("/t")
    public String transaction() {
        try {
            service.insert();
        } catch (Exception e) {
        }
        return "事务回滚";
    }

    /**
     * 事务不生效(不回滚)
     */
    @RequestMapping("/t2")
    public String transaction2() {
        try {
            service.insert2();
        } catch (Exception e) {
        }
        return "事务不生效(不回滚)";
    }

    /**
     * 事务生效
     */
    @RequestMapping("/t3")
    public String transaction3() {
        try {
            service.insert3();
        } catch (Exception e) {
        }
        return "事务生效";
    }
}
