package com.ynsh.modules.present.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ynsh.common.persistence.entity.DataEntity;

import java.util.Date;

/**
 * Created by syb on 18-1-22.
 */
public class PresentType extends DataEntity<PresentType,Long>{

    private String presentName;
    private Date startDate;
    private Date endDate;
    private String orgCode;
    private String distributeType;

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static final String DISTRIBUTE_TYPE_EVERYONE = "1";
    public static final String DISTRIBUTE_TYPE_SPECIFIC = "2";
}
