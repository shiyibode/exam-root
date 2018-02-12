package com.ynsh.modules.bldk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ynsh.common.persistence.entity.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by syb on 18-1-8.
 */
public class NonPerformingLoan extends DataEntity<NonPerformingLoan,Long>{

    private String orgCode;                 //所属机构
    private String accountNo;               //贷款帐号
    private String customerName;            //客户名称
    private BigDecimal principal;           //核销本金
    private BigDecimal interest;            //核销利息
    private BigDecimal compoundInterest;    //核销时复利
    private BigDecimal fxRate;              //罚息利率
    private Date hxDate;                    //核销日期
    private String interestTerm;            //结息周期
    private BigDecimal beforeHxInterest;    //核销前最后一次结息时的利息总金额，用于计算复利


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getCompoundInterest() {
        return compoundInterest;
    }

    public void setCompoundInterest(BigDecimal compoundInterest) {
        this.compoundInterest = compoundInterest;
    }

    public BigDecimal getFxRate() {
        return fxRate;
    }

    public void setFxRate(BigDecimal fxRate) {
        this.fxRate = fxRate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getHxDate() {
        return hxDate;
    }

    public void setHxDate(Date hxDate) {
        this.hxDate = hxDate;
    }

    public String getInterestTerm() {
        return interestTerm;
    }

    public void setInterestTerm(String interestTerm) {
        this.interestTerm = interestTerm;
    }

    public BigDecimal getBeforeHxInterest() {
        return beforeHxInterest;
    }

    public void setBeforeHxInterest(BigDecimal beforeHxInterest) {
        this.beforeHxInterest = beforeHxInterest;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
