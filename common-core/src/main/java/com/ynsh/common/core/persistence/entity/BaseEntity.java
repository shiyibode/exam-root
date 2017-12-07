package com.ynsh.common.core.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * Entity支持类
 */
public abstract class BaseEntity<T, PK extends Serializable> implements Serializable, Comparable {

	/**
	 * 实体编号（唯一标识）
	 */
	protected PK id;

	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;
	
	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
	protected Map<String, String> sqlMap;
	
	/**
	 * 是否是新记录（默认：false），
	 * 用于service判断执行插入还是更新
	 */
	protected boolean isNewRecord = false;

	/**
	 * ID 是否自动生成,
	 * 默认为false, 不生成ID,ID由数据库生成
	 * 设置为true后, 使用自定义ID (程序自动生成UUID)
	 */
	protected boolean isAutoGenId = false;

	public BaseEntity() {
		
	}
	
	public BaseEntity(PK id) {
		this();
		this.setId(id);
	}

	/**
	 * 实体编号, 只能通过子类实现，父类实现mybatis无法读取
	 * @return
     */
	public abstract PK getId();

    /**
     * 实体编号, 只能通过子类实现，父类实现mybatis无法读取
     * @param id
     */
	public abstract void setId(PK id);
	

	@JsonIgnore
	@XmlTransient
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}
	
	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}

	@JsonIgnore
	@XmlTransient
	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = Maps.newHashMap();
		}
		return sqlMap;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
	
	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();
	
	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();


	public boolean getIsNewRecord() {
        return isNewRecord || getId() == null || StringUtils.isBlank(getId().toString());
    }

	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public boolean getIsAutoGenId() {
		return this.isAutoGenId;
	}

	public void setIsAutoGenId(boolean isAutoGenId) {
		this.isAutoGenId = isAutoGenId;
	}

	/**
	 * 全局变量对象
	 */
	@JsonIgnore
	public Global getGlobal() {
		return Global.getInstance();
	}
	
	/**
	 * 获取数据库名称
	 */
	@JsonIgnore
	public String getDbName(){
		return Global.getConfig("jdbc.type");
	}
	
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?, ?> that = (BaseEntity<?, ?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

	@Override
	public int compareTo(Object obj) {
		if (null == obj) {
			return -1;
		}
		if (this == obj) {
			return -1;
		}
		if (!getClass().equals(obj.getClass())) {
			return -1;
		}

		BaseEntity<?, ?> that = (BaseEntity<?, ?>) obj;
		if (this.getId() instanceof Long && that.getId() instanceof Long) {
			return ((Long) this.getId()).compareTo((Long)that.getId());
		}
		if (this.getId() instanceof String && that.getId() instanceof String) {
			return ((String) this.getId()).compareTo((String)that.getId());
		}
		if (this.getId() instanceof UUID && that.getId() instanceof UUID) {
		    return ((UUID) this.getId()).compareTo((UUID) that.getId());
        }

		return -1;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
	/**
	 * 删除标记（false：正常；true：删除；）
	 */
	public static final Boolean DEL_FLAG_NORMAL = false;
	public static final Boolean DEL_FLAG_DELETE = true;
	//public static final String DEL_FLAG_AUDIT = "2";
	
}
