package com.ynsh.common.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.modules.sys.entity.User;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;

import java.io.Serializable;

/**
 * Created by chenjianjun on 16/8/4.
 */
public abstract class DataEntity<T, PK extends Serializable> extends com.ynsh.common.core.persistence.entity.DataEntity<T, PK> {

    /**
     * 对象创建者
     */
    protected User createBy;  //创建者

    /**
     * 对象最后一次更新者
     */
    protected User updateBy;  //更新者


    protected UserOrganization currentUserOrganization;


    public DataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public DataEntity(PK id) {
        super(id);
    }

    @Override
    public void preInsert() {
        User user = UserUtils.getUser();
        if (null != user && null != user.getId()) {
            this.setCreateBy(user);
            this.setUpdateBy(user);
        }
        super.preInsert();
    }

    @Override
    public void preUpdate() {
        User user = UserUtils.getUser();
        if (null != user && null != user.getId()){
            this.setUpdateBy(user);
        }
        super.preUpdate();
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    @JsonIgnore
    public UserOrganization getCurrentUserOrganization() {
        if(currentUserOrganization == null){
            currentUserOrganization = UserUtils.getUserOrganization();
        }
        return currentUserOrganization;
    }

    public void setCurrentUserOrganization(UserOrganization currentUserOrganization) {
        this.currentUserOrganization = currentUserOrganization;
    }
}
