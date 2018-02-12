package com.ynsh.modules.bldk.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ynsh.common.persistence.entity.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by syb on 18-1-8.
 */
public class RepaymentRecord extends DataEntity<RepaymentRecord,Long> {

    private BigDecimal repaymentPrincipal;          //归还本金
    private BigDecimal repaymentInterest;           //归还利息
    private BigDecimal repaymentCompoundInterest;   //归还复利
    private Date repaymentDate;                     //归还日期
    private Long nonPerformingLoanId;               //不良贷款id

    public BigDecimal getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public BigDecimal getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(BigDecimal repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public BigDecimal getRepaymentCompoundInterest() {
        return repaymentCompoundInterest;
    }

    public void setRepaymentCompoundInterest(BigDecimal repaymentCompoundInterest) {
        this.repaymentCompoundInterest = repaymentCompoundInterest;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Long getNonPerformingLoanId() {
        return nonPerformingLoanId;
    }

    public void setNonPerformingLoanId(Long nonPerformingLoanId) {
        this.nonPerformingLoanId = nonPerformingLoanId;
    }

    //以下3个属性用于搜索
    private String orgCode;
    private String accountNo;
    private String customerName;

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
