package com.ynsh.modules.sys.web.filter;

import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.utils.WriteLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenjianjun on 2016/10/20.
 */
public class WriteLogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private FilterConfig filterConfig;

    private String[] includes;

    private String[] excludes;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.includes = null;
        this.excludes = null;

        String includesStr = filterConfig.getInitParameter("includes");
        if (StringUtils.isNotEmpty(includesStr)) {
            this.includes = includesStr.split(";");
        }
        String excludesStr = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotEmpty(excludesStr)) {
            this.excludes = excludesStr.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest)servletRequest).getRequestURI();
        if ((this.includes == null || this.isContains(uri, this.includes)) && (this.excludes == null || !this.isContains(uri, this.excludes))) {
            long beginTime = System.currentTimeMillis();
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

            filterChain.doFilter(requestWrapper, responseWrapper);

            String requestBody = new String(requestWrapper.getContentAsByteArray());
            String responseBody = new String(responseWrapper.getContentAsByteArray());
            // Do not forget this line after reading response content or actual response will be empty!
            responseWrapper.copyBodyToResponse();

            // Write request and response body, headers, timestamps etc. to log files
            long endTime = System.currentTimeMillis();
            WriteLogUtils.saveOperationLog(requestWrapper, requestBody, responseBody, beginTime, endTime);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    private boolean isContains(String uri, String[] regx) {
        if (null == regx || regx.length <= 0) {
            return false;
        }

        for (int i = 0; i < regx.length; i++) {
            if (uri.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return false;
    }
}
