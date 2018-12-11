package liu.chi.mongo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liu.chi.mongo.repository.App;
import liu.chi.mongo.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * mongoRepository
 *
 * @author liuchi
 * @date 2018-09-15 16:14
 */
@RestController
public class RepositoryController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppRepository repository;

    @RequestMapping("/queryAll")
    public String queryAll() {
        List<App> all = repository.findAll();
        System.out.println("总记录数:" + all.size());
        all.forEach(a -> {
            try {
                System.out.println(objectMapper.writeValueAsString(a));
            } catch (JsonProcessingException e) {
            }
        });
        return "成功,请看控台";
    }

    @RequestMapping("/insert")
    public String insert() {
        for (int i = 1; i < 5; i++) {
            App app = new App();
            app.setAddress("江苏" + i);
            app.setAge(25);
            app.setAsserts(new BigDecimal("1000" + i));
            app.setName("刘驰");
            repository.save(app);
            System.out.println(app.getId());

            App app2 = new App();
            app2.setAddress("江苏" + i);
            app2.setAge(25);
            app2.setAsserts(new BigDecimal("1000"));
            app2.setName("刘驰");
            repository.save(app2);
        }
        return "成功,请查库";
    }

    @RequestMapping("/queryPage")
    public String pageQuery() {
        App app = new App();
        app.setName("刘驰");

        Example<App> example = Example.of(app);

        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "address");
        orders.add(order);
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "asserts");
        orders.add(order2);
        Sort sort = Sort.by(orders);
        //mongo分页为0开始
        PageRequest pageable =  PageRequest.of(2, 3, sort);

        Page<App> all = repository.findAll(example, pageable);
        System.out.println("总页数:" + all.getTotalPages());
        System.out.println("查询出结果数:" + all.getTotalElements());
        all.getContent().forEach(a -> {
            try {
                System.out.println(objectMapper.writeValueAsString(a));
            } catch (JsonProcessingException e) {
            }
        });
        return "成功,请看控台";
    }

}
