package liu.chi.tkmybatis.dal.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "`t_person`")
public class TPerson {
    /**
     * 主键
     */
    @Id
    @Column(name = "`s_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer sId;

    /**
     * 姓名
     */
    @Column(name = "`s_name`")
    private String sName;

    /**
     * 年龄
     */
    @Column(name = "`age`")
    private Integer age;

    /**
     * 身份证号码
     */
    @Column(name = "`cid`")
    private String cid;

    /**
     * 性别，0男，1女
     */
    @Column(name = "`gender`")
    private Boolean gender;

    /**
     * 出生时间
     */
    @Column(name = "`birthday`")
    private LocalDateTime birthday;

    /**
     * 资产
     */
    @Column(name = "`assets`")
    private Long assets;

    /**
     * 住址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "`modify_time`")
    private LocalDateTime modifyTime;
}