package liu.chi.mongo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author liuchi
 * @date 2018-09-22 10:53
 */
public interface AppRepository extends MongoRepository<App, String> {
}
