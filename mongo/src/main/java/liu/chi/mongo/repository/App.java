package liu.chi.mongo.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @desc:
 * @author：liuchi
 * @date： 2018/02/12 14:49
 */
@Data
@Document(collection = "app")
public class App implements Serializable {
    private static final long serialVersionUID = -1802867546692991604L;
    @Id
    private String id;

    private String name;
    private Integer age;
    private BigDecimal asserts;
    private String address;
}
