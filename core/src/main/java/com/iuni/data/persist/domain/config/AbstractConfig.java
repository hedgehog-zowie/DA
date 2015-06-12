package com.iuni.data.persist.domain.config;

import com.iuni.data.common.ConfigConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@MappedSuperclass
public class AbstractConfig implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Column(name = "\"DESC\"")
    protected String desc;

    @Column(name = "STATUS")
    protected Integer status;

    @Column(name = "CANCEL_FLAG")
    protected Integer cancelFlag;

    @Column(name = "CREATE_BY")
    protected String createBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    protected Date createDate;

    @Column(name = "UPDATE_BY")
    protected String updateBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_DATE")
    protected Date updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(Integer cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void cancel(String userName){
        this.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_CANCEL);
        setCreateInfo(userName);
        setUpdateInfo(userName);
    }

    public void setBasicInfoForCreate(String userName) {
        this.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        this.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        setCreateInfo(userName);
        setUpdateInfo(userName);
    }

    public void setBasicInfoForUpdate(String userName){
        this.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        this.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        setUpdateInfo(userName);
    }

    public void setBasicInfoForCancel(String userName){
        this.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_CANCEL);
        setUpdateInfo(userName);
    }

    private void setCreateInfo(String userName) {
        setCreateBy(userName);
        setCreateDate(new Date());
    }

    private void setUpdateInfo(String userName) {
        setUpdateBy(userName);
        setUpdateDate(new Date());
    }

}
