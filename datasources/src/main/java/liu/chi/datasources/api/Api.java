package liu.chi.datasources.api;

import liu.chi.datasources.dal.model.TPerson;
import liu.chi.datasources.read.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuchi
 * @date 2018-12-24 17:13
 */
@RestController
public class Api {
    @Autowired
    private ReadService readService;

    @RequestMapping("/read")
    public List<TPerson> read() {
        return readService.read();
    }
}
