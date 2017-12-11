package com.ynsh.modules.sys.security.session;

import com.ynsh.modules.sys.utils.WriteLogUtils;
import org.apache.shiro.session.Session;

/**
 * Created by chenjianjun on 2016/10/21.
 */
public class CacheSessionDAO extends com.ynsh.common.core.shiro.session.CacheSessionDAO {

    @Override
    protected void doDelete(Session session) {
        String sessionId = (String) session.getId();
        Long logoutTime = (Long)session.getAttribute("logoutTime");
        String logoutType = (String)session.getAttribute("logoutType");
        super.doDelete(session);
        WriteLogUtils.updateLoginLog(sessionId, logoutTime, logoutType);
    }
}
