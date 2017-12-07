package com.ynsh.common.core.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.utils.IdGen;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据Entity类
 */
public abstract class DataEntity<T, PK extends Serializable> extends BaseEntity<T, PK> {

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 备注
	 */
	protected String remarks;

	/**
	 * 创建日期
	 */
	protected Date createTime;

	/**
	 * 更新日期
	 */
	protected Date updateTime;

	protected Boolean delFlag; 	// 删除标记（false：正常；true：删除）

	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(PK id) {
		super(id);
	}


	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JsonIgnore
	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}


	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert() {
		// 自动生ID为UUID
		if (this.getIsAutoGenId()){
			setId((PK)IdGen.uuid());
		}

        this.updateTime = new Date();
        this.createTime = this.updateTime;
	}

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate(){
        this.updateTime = new Date();
    }
}
