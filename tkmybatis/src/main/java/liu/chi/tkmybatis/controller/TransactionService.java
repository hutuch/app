package liu.chi.tkmybatis.controller;

import liu.chi.tkmybatis.dal.mapper.TPersonMapper;
import liu.chi.tkmybatis.dal.model.TPerson;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author liuchi
 * @date 2018-09-22 15:36
 */
@Service
public class TransactionService {
    @Autowired
    private TPersonMapper mapper;

    /**
     * 事务生效
     * 在同一个类中调用自己已经通过spring托管的类中的方法
     */
    public String insert3() throws InterruptedException {
        //@EnableAspectJAutoProxy(exposeProxy = true)暴露代理
        TransactionService service = (TransactionService) AopContext.currentProxy();
        return service.insert();
    }

    /**
     * 事务不生效
     */
    public String insert2() throws InterruptedException {
        return insert();
    }

    /**
     * 事务生效
     */
    @Transactional
    public String insert() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            TPerson person = new TPerson();
            person.setSName("ss" + i);
            person.setAddress("jiangsu" + i);
            person.setBirthday(LocalDateTime.now());
            mapper.insert(person);
            TimeUnit.SECONDS.sleep(2);
        }

        throw new RuntimeException();
    }
}
