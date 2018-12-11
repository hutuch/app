package liu.chi.web.pojo;

/**
 * @author liuchi
 * @date 2018-09-14 17:30
 */
public enum Error {
    HAHA(0, "故意的");
    private Integer code;
    private String msg;

    Error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
