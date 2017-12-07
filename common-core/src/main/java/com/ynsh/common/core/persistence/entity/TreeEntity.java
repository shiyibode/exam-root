package com.ynsh.common.core.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据Entity类
 */
public abstract class TreeEntity<T, PK extends Serializable> extends DataEntity<T, PK> {

	protected PK parentId;	// 父级编号
	protected String parentIds; // 所有父级编号
	protected Integer sort;		// 排序

	protected List<T> children;

	protected Boolean editable;

	public TreeEntity() {
		super();
		editable = true;
	}
	
	public TreeEntity(PK id) {
		super(id);
		editable = true;
	}
	
    /**
     * 父对象ID，通过子类实现
     * @return
     */
    public abstract PK getParentId();

	/**
	 * 父对象ID，通过子类实现
	 * @return
	 */
	public abstract void setParentId(PK parentId);

	@JsonIgnore
    public abstract  PK getRootId();

    /**
     * 父对象是否为空
     * @return
     */
	@JsonIgnore
    public boolean isParentBlank() {
        return parentId == null || getRootId().equals(getParentId());
    }

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@JsonInclude
	public List<T> getChildren() {
		if (null == this.children) {
			children = new ArrayList<>();
		}
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public Boolean getEditable() {
		return this.getParentId() != null && this.getParentId().equals(this.getRootId()) ? false : editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean isLeaf() {
		return this.getChildren().size() == 0 ? true : false;
	}

	public Boolean getExpanded() {
		return this.getChildren().size() > 0;
		//return this.getType().equalsIgnoreCase("menu_group");
	}

	//	public PK getParentId() {
//        PK id = null;
//		if (parent != null){
//			id = (PK)Reflections.getFieldValue(parent, "id");
//		}
////		return StringUtils.isNotBlank(id) ? id : "0";
//	}
}
