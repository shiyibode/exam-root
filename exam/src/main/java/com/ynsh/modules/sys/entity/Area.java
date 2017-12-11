package com.ynsh.modules.sys.entity;

import com.ynsh.common.persistence.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 区域Entity
 */
public class Area extends TreeEntity<Area, Long> {

	private String code; 	// 区域编码
	private String iconCls; // 图标
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	
	public Area(){
		super();
	}

	public Area(Long id){
		super(id);
	}

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


	@Override
	public Long getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

    @Override
    public Long getRootId() {
        return 0L;
    }

    @Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Override
	public String toString() {
		return name;
	}

}