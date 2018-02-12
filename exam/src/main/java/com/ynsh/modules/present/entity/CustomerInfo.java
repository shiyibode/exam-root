package com.ynsh.modules.present.entity;

import com.ynsh.common.core.persistence.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by syb on 18-2-7.
 */
public class CustomerInfo implements Serializable{

    private String name;
    private String customerNo;
    private String identityNumber;
    private String accountNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
