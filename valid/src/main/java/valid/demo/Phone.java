package valid.demo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author liuchi
 * @date 2018-10-12 11:12
 */
@Data
public class Phone {
    @NotEmpty(message = "品牌不能为空")
    private String brand;

    @NotNull(message = "价钱必能为空")
    private BigDecimal price;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",message = "生产日期不正确")
    private String date;
}
