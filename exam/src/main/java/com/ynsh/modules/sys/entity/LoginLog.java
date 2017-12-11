package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.persistence.entity.DataEntity;
import com.ynsh.modules.sys.utils.UserUtils;

import java.util.Date;

/**
 * Created by chenjianjun on 2016/10/19.
 */
public class LoginLog extends DataEntity<LoginLog, Long> {

    private String sessionId;
    private String ipAddr;
    private Date loginTime;
    private Long userId;
    private String userCode;
    private String userName;
    private Date logoutTime;
    private String logoutType;
    private String method;
    private String userAgent;

    private Date beginDate;		//开始日期(用于查询)
    private Date endDate;		//结束日期(用于查询)

    /**
     * 当前用户
     */
    protected UserOrganization userOrganization;

    protected Boolean onlyUser;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(String logoutType) {
        this.logoutType = logoutType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    public UserOrganization getUserOrganization() {
        if(userOrganization == null){
            userOrganization = UserUtils.getUserOrganization();
        }
        return userOrganization;
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        this.userOrganization = userOrganization;
    }

    public Boolean getOnlyUser() {
        return onlyUser;
    }

    public void setOnlyUser(Boolean onlyUser) {
        this.onlyUser = onlyUser;
    }

    @Override
    public boolean getIsNewRecord() {
        return this.isNewRecord;
    }

    @Override
    public void preInsert() {
        User user = UserUtils.getUser();
        if (null != user && null != user.getId()) {
            this.setSessionId((String)UserUtils.getSession().getId());
            this.setUserId(user.getId());
            this.setUserCode(user.getCode());
            this.setUserName(user.getName());
            this.setLoginTime(new Date());
        }
        super.preInsert();
    }

    @Override
    public void preUpdate() {
        this.setLogoutTime(new Date());
        super.preUpdate();
    }
}
