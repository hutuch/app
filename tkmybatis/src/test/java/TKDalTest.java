import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import liu.chi.tkmybatis.TkMybatisApplication;
import liu.chi.tkmybatis.dal.mapper.TPersonMapper;
import liu.chi.tkmybatis.dal.model.TPerson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuchi
 * @date 2018-09-22 09:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TkMybatisApplication.class)
public class TKDalTest {

    @Autowired
    private TPersonMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void count() {
        System.out.println(mapper.selectAll().size());
    }

    @Test
    public void getAll() {
        mapper.selectAll().forEach(p-> {
            try {
                System.out.println(objectMapper.writeValueAsString(p));
            } catch (JsonProcessingException e) {

            }
        });
    }

    @Test
    public void getLiu() {
        TPerson person = new TPerson();
        person.setSName("liu");

        PageHelper.startPage(2, 1);
        mapper.select(person).forEach(p-> {
            try {
                System.out.println(objectMapper.writeValueAsString(p));
            } catch (JsonProcessingException e) {
            }
        });

    }
}
