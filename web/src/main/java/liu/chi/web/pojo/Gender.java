package liu.chi.web.pojo;

/**
 * @author liuchi
 * @date 2018-09-12 11:07
 */
public enum Gender {
    MALE(0, "男"),

    FEMALE(1, "女");

    private Integer code;
    private String description;

    Gender(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
