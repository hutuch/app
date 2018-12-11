package valid.demo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liuchi
 * @date 2018-10-12 11:11
 */
@Data
public class Person {
    @NotEmpty(message = "name不能为空")
    private String name;

    @Valid
    @NotNull
    private List<Phone> phones;

    @Valid
    private Phone phone;
}
