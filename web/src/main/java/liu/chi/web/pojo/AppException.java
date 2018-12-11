package liu.chi.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuchi
 * @date 2018-09-14 17:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends Exception {
    private Error error;
}
