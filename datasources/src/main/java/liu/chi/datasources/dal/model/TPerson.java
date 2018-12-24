package liu.chi.datasources.dal.model;

import java.util.Date;
import javax.persistence.*;

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
    private Date birthday;

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
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "`modify_time`")
    private Date modifyTime;

    /**
     * 获取主键
     *
     * @return s_id - 主键
     */
    public Integer getsId() {
        return sId;
    }

    /**
     * 设置主键
     *
     * @param sId 主键
     */
    public void setsId(Integer sId) {
        this.sId = sId;
    }

    /**
     * 获取姓名
     *
     * @return s_name - 姓名
     */
    public String getsName() {
        return sName;
    }

    /**
     * 设置姓名
     *
     * @param sName 姓名
     */
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取身份证号码
     *
     * @return cid - 身份证号码
     */
    public String getCid() {
        return cid;
    }

    /**
     * 设置身份证号码
     *
     * @param cid 身份证号码
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * 获取性别，0男，1女
     *
     * @return gender - 性别，0男，1女
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * 设置性别，0男，1女
     *
     * @param gender 性别，0男，1女
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * 获取出生时间
     *
     * @return birthday - 出生时间
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生时间
     *
     * @param birthday 出生时间
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取资产
     *
     * @return assets - 资产
     */
    public Long getAssets() {
        return assets;
    }

    /**
     * 设置资产
     *
     * @param assets 资产
     */
    public void setAssets(Long assets) {
        this.assets = assets;
    }

    /**
     * 获取住址
     *
     * @return address - 住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置住址
     *
     * @param address 住址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}