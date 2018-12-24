package liu.chi.datasources.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liu.chi.datasources.conf.ReadOnly;
import liu.chi.datasources.dal.mapper.TPersonMapper;
import liu.chi.datasources.dal.model.TPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuchi
 * @date 2018-12-24 16:10
 */
@Service
public class ReadService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TPersonMapper mapper;

    public List<TPerson> read() {
        List<TPerson> tPeople = mapper.selectAll();
        System.out.println("共查询:" + tPeople.size());
        tPeople.forEach((t)-> {
            try {
                System.out.println(objectMapper.writeValueAsString(t));
            } catch (JsonProcessingException e) {

            }
        });

        return tPeople;
    }
}
