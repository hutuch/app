package liu.chi.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * mongodb 安装在windows
 * @author liuchi
 * @date 2018-12-11 15:45
 */
@EnableMongoRepositories(basePackages = "liu.chi.mongo.repository")
@SpringBootApplication
public class MongoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }
}
