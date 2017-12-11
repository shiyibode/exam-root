package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.common.persistence.entity.DataEntity;
import com.ynsh.modules.sys.utils.UserUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by chenjianjun on 2016/10/19.
 */
public class OperationLog extends DataEntity<OperationLog, Long> {

    private String sessionId;
    private String ipAddr;
    private Date opTime;
    private Long costTime;
    private Long userId;
    private String userCode;
    private String userName;
    private String title;
    private String method;
    private String requestUri;
    private String requestBody;
    private String responseBody;
    private String params;
    private String userAgent;

    private Date beginDate;		//开始日期(用于查询)
    private Date endDate;		//结束日期(用于查询)

    /**
     * 当前用户
     */
    protected UserOrganization currentUserOrganization;

    protected Boolean onlyUser;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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
    public UserOrganization getCurrentUserOrganization() {
        if(currentUserOrganization == null){
            currentUserOrganization = UserUtils.getUserOrganization();
        }
        return currentUserOrganization;
    }

    public void setCurrentUserOrganization(UserOrganization currentUserOrganization) {
        this.currentUserOrganization = currentUserOrganization;
    }

    public Boolean getOnlyUser() {
        return onlyUser;
    }

    public void setOnlyUser(Boolean onlyUser) {
        this.onlyUser = onlyUser;
    }

    /**
     * 设置请求参数
     * @param paramMap
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setParams(Map paramMap) {
        if (paramMap == null){
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.params = params.toString();
    }

    @Override
    public void preInsert() {
        User user = UserUtils.getUser();
        if (null != user && null != user.getId()) {
            this.setSessionId((String)UserUtils.getSession().getId());
            this.setUserId(user.getId());
            this.setUserCode(user.getCode());
            this.setUserName(user.getName());
        }
        super.preInsert();
    }
}
