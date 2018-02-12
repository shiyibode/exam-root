package com.ynsh.modules.bldk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by syb on 18-1-15.
 */
public class DailyPrincipalInterest {

    private Date date;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal compoundInterest;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
