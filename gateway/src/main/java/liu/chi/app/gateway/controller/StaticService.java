package liu.chi.app.gateway.controller;

import org.springframework.stereotype.Repository;

/**
 * @author liuchi
 * @date 2018-12-11 10:49
 */
@Repository
public class StaticService {
    public String getStr() {
        return "aaaA中文";
    }
}
