package liu.chi.securuty.web;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author liuchi
 * @date 2018-11-07 15:16
 */
@Data
public class Stu implements Serializable {
    private static final long serialVersionUID = 3922074756373714501L;

    private String name;
    private Integer age;
    private BigDecimal assets;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

}
