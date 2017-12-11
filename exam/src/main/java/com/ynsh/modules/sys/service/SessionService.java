package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.BaseService;
import com.ynsh.common.core.shiro.session.SessionDAO;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by chenjianjun on 2016/9/29.
 */
@Service
@Transactional(readOnly = true)
public class SessionService extends BaseService implements InitializingBean {

    @Autowired
    private SessionDAO sessionDao;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    public void setSessionDao(SessionDAO sessionDao) {
        this.sessionDao = sessionDao;
    }


    /**
     * 获取活动会话
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @return
     */
    public Collection<Session> getActiveSessions(boolean includeLeave) {
        return sessionDao.getActiveSessions(includeLeave);
    }

    /**
     * 获取活动会话
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @param principal 根据登录者对象获取活动会话
     * @param filterSession 不为空，则过滤掉（不包含）这个会话。
     * @return
     */
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
        return sessionDao.getActiveSessions(includeLeave, principal, filterSession);
    }

    public void delete(Session session) {
        sessionDao.delete(session);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
