package com.ynsh.modules.sys.service;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.service.BaseService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
    /**
     * 获取Key加载信息
     */
    public static boolean printKeyLoadMessage(){
        String productName = StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava(Global.getConfig("productName")));
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n======================================================================\r\n");
        sb.append("\r\n    欢迎使用 "+ productName + "  - Powered By         \r\n");
        sb.append("\r\n======================================================================\r\n");
        System.out.println(sb.toString());
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
