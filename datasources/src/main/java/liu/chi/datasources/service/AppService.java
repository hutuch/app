package liu.chi.datasources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuchi
 * @date 2018-09-23 15:34
 */
@Service
public class AppService {
    @Autowired
    private AppDal dal;

    public void readService() {
        dal.read();
    }

    public void writeService() {
        dal.write();
    }


    public void execute() {
        dal.write();

        dal.read();
    }
}
