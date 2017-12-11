package com.ynsh.modules.sys.security.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * Created by chenjianjun on 16/8/3.
 * 退出系统过滤器
 */
@Service
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);

        try {
            Session session = subject.getSession();
            session.setAttribute("logoutTime", Long.valueOf(System.currentTimeMillis()));
            session.setAttribute("logoutType", "正常退出");
            subject.logout();
        } catch (SessionException var6) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }

        HttpServletRequest req = WebUtils.toHttp(request);
        String xmlHttpRequest = req.getHeader("X-Requested-With");
        if (null != xmlHttpRequest && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("{success: true, msg:\"退出系统成功!\"}");
            out.flush();
            out.close();
        } else {
            this.issueRedirect(request, response, this.getRedirectUrl());
        }

        return false;
    }
}
