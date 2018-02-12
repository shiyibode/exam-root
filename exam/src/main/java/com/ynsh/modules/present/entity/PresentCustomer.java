package com.ynsh.modules.present.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ynsh.common.persistence.entity.DataEntity;

import java.util.Date;

/**
 * Created by syb on 18-1-22.
 */
public class PresentCustomer extends DataEntity<PresentCustomer,Long> {

    private Long presentId;
    private String customerName;
    private String identityNumber;
    private String distributeFlag;
    private String orgCode;
    private Long organizationId;
    private String phoneNumber;
    private String accountNo;

    public Long getPresentId() {
        return presentId;
    }

    public void setPresentId(Long presentId) {
        this.presentId = presentId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getDistributeFlag() {
        return distributeFlag;
    }

    public void setDistributeFlag(String distributeFlag) {
        this.distributeFlag = distributeFlag;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    private String presentName;

    private String tellerCode;

    private Date distributeDate;

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public String getTellerCode() {
        return tellerCode;
    }

    public void setTellerCode(String tellerCode) {
        this.tellerCode = tellerCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDistributeDate() {
        return distributeDate;
    }

    public void setDistributeDate(Date distributeDate) {
        this.distributeDate = distributeDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
