package liu.chi.datasources.service;

import liu.chi.datasources.conf.ReadOnly;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuchi
 * @date 2018-12-13 09:22
 */
@Service
public class StuService {

    public void read() {
        System.out.println("read");
    }

    @ReadOnly
    public void get() {

    }

    @Transactional
    public void insert() {

    }
}
