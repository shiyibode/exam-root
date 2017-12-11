package com.ynsh.modules.sys.security.shiro;

import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.utils.UserUtils;
import com.ynsh.modules.sys.utils.WriteLogUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 表单验证过滤类
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    public static final String DEFAULT_MESSAGE_PARAM = "message";

    private String messageParam = DEFAULT_MESSAGE_PARAM;

    private static final Logger logger = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);

        logger.info("username: {}, password: {}", username, password);

        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
    }


    public String getMessageParam() {
        return messageParam;
    }

    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
//        logger.info("拦截测试: method: {}, uri: {}", req.getMethod(), req.getRequestURI());
        return super.onAccessDenied(request, response);
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request, ServletResponse response) {

        logger.info("onLoginFailure: ");

        String className = e.getClass().getName();
        String message = "";
        if (UnknownAccountException.class.getName().equals(className)) {
            message = "用户名/柜员号错误, 请输入正确的用户名/柜员号!";
        } else if (IncorrectCredentialsException.class.getName().equals(className)) {
            message = "密码错误, 请输入正确的密码; 如果忘记密码请联系管理员重置您的密码!";
        } else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        } else {
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        UserUtils.updateUserLoginInfo();
        WriteLogUtils.saveLoginLog((HttpServletRequest) request);
        return super.onLoginSuccess(token, subject, request, response);
    }
}