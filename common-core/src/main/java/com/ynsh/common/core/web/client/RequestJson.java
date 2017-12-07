package com.ynsh.common.core.web.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.ynsh.common.core.mapper.JsonMapper;
import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.persistence.Sorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianjun on 16/7/5.
 */

public class RequestJson<T, PK extends Serializable> {
    private PK id;

    private List<T> entities;   //用于添加、修改、删除

    private T filter;           //用于SQL过滤

    private Page<T> page;       //用于分页、排序

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public RequestJson() {
        //logger.info("RequestJson constractor !!!!");
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        //logger.info("setFilter: {}", filter);
        this.filter = filter;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    /////////////////////////////////////
    public void setNode(PK node) {
        this.setId(node);
    }

    public void setData(List<T> data) {
        logger.info("setData: {}", data);
        this.setEntities(data);
    }

    @JsonProperty("page")
    public void setPageNo(int pageNo) {
        if (pageNo > 0) {
            if (null == this.page) {
                page = new Page<T>();
            }
            page.setPageNo(pageNo);
        }
    }

    public void setLimit(int pageSize) {
        if (pageSize > 0) {
            if (null == this.page) {
                page = new Page<T>();
            }
            page.setPageSize(pageSize);
        }
    }

    @JsonProperty("sort")
    public void setSort(String sort) {
        JsonMapper jsonMapper = JsonMapper.getInstance();
        JavaType javaType = jsonMapper.createCollectionType(ArrayList.class, Sorter.class);
        List<Sorter> sorters = (List<Sorter>)jsonMapper.fromJson(sort, javaType);
        if (null == this.page) {
            page = new Page<T>();
        }
        page.setSorters(sorters);
    }

    @JsonIgnore
    public void setStart(int start) {

    }


}
