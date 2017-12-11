package com.ynsh.modules.sys.entity;

import com.ynsh.common.persistence.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 字典Entity
 */
public class Dict extends TreeEntity<Dict, Long> {
    private String value;   //数据值
    private String type;    //类型
    private String description; //描述

    public Dict() {
        super();
    }

    public Dict(Long id) {
        super(id);
    }

    public Dict(String name, String value) {
        this.name = name;
        this.value = value;
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Length(min=1, max=100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Length(min=1, max=100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min=0, max=512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
