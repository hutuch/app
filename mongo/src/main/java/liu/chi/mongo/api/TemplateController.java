package liu.chi.mongo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liu.chi.mongo.repository.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuchi
 * @date 2018-09-22 10:59
 */
@RestController
public class TemplateController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate template;

    @RequestMapping("/save")
    public String save() {
        App model = new App();
        model.setName("刘驰");
        model.setAge(23);
        model.setAddress("江苏");
        model.setAsserts(new BigDecimal("17700"));
        template.save(model);
        System.out.println(model.getId());
        return "成功,请看控台";
    }

    @RequestMapping("/dropAll")
    public String delete() {
        template.dropCollection(App.class);
        return "成功,请看控台";
    }

    @RequestMapping("/query")
    public String query() throws JsonProcessingException {
        App byId = template.findById("5b9cc3b2ca6c9d06389f9386", App.class);
        System.out.println("id查出:" + objectMapper.writeValueAsString(byId));

        Query query = new Query();
        Criteria criteria = Criteria.where("address").is("江苏1").and("name").is("刘驰");
        query.addCriteria(criteria);

        List<App> apps = template.find(query, App.class);
        System.out.println("共计:" + apps.size() + "条");
        apps.forEach(app -> {
            try {
                System.out.println(objectMapper.writeValueAsString(app));
            } catch (JsonProcessingException e) {
            }
        });

        return "query()成功,请看控台";
    }
}
