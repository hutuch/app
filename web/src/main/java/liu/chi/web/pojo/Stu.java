package liu.chi.web.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liuchi
 * @date 2018-09-12 11:06
 */
@Data
public class Stu implements Serializable {
    private static final long serialVersionUID = -1141874380862630432L;

    private String name;
    private Integer age;
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birthday;

}
