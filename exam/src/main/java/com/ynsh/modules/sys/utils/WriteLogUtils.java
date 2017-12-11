package com.ynsh.modules.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynsh.common.utils.CacheUtils;
import com.ynsh.common.utils.SpringContextHolder;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.dao.LoginLogDao;
import com.ynsh.modules.sys.dao.MenuDao;
import com.ynsh.modules.sys.dao.OperationLogDao;
import com.ynsh.modules.sys.entity.LoginLog;
import com.ynsh.modules.sys.entity.Menu;
import com.ynsh.modules.sys.entity.OperationLog;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjianjun on 2016/10/20.
 */
public class WriteLogUtils {
    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    private static LoginLogDao loginLogDao = SpringContextHolder.getBean(LoginLogDao.class);
    private static OperationLogDao operationLogDao = SpringContextHolder.getBean(OperationLogDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    //private static Logger logger = LoggerFactory.getLogger(WriteLogUtils.class);

    public static void saveLoginLog(HttpServletRequest request) {
        LoginLog loginLog = new LoginLog();
        loginLog.setIsNewRecord(true);
        loginLog.setIpAddr(StringUtils.getRemoteAddr(request));
        loginLog.setMethod(request.getMethod());
        loginLog.setUserAgent(request.getHeader("user-agent"));

        // 使用线程异步保存日志
        new SaveLogThread(loginLog).start();
    }

    public static void updateLoginLog(String sessionId, Long logoutTime, String logoutType) {
        LoginLog loginLog = new LoginLog();
        loginLog.setIsNewRecord(false);
        if (StringUtils.isNotEmpty(sessionId)) {
            loginLog.setSessionId(sessionId);
        } else {
            return;
        }
        if (null != logoutTime) {
            loginLog.setLogoutTime(new Date(logoutTime.longValue()));
        } else {
            loginLog.setLogoutTime(new Date());
        }
        if (StringUtils.isNotEmpty(logoutType)) {
            loginLog.setLogoutType(logoutType);
        } else {
            loginLog.setLogoutType("超时退出");
        }
        // 使用线程异步保存日志
        new SaveLogThread(loginLog).start();
    }

    /**
     * 保存操作日志
     */
    public static void saveOperationLog(HttpServletRequest request, String requestBody, String responseBody, long beginTime, long endTime){
        OperationLog operationLog = new OperationLog();
        operationLog.setOpTime(new Date(beginTime));
        operationLog.setCostTime(endTime - beginTime);
        operationLog.setTitle(getMenuNamePath(request));
        operationLog.setIpAddr(StringUtils.getRemoteAddr(request));
        operationLog.setMethod(request.getMethod());
        operationLog.setRequestUri(request.getRequestURI());
        operationLog.setParams(request.getParameterMap());
        operationLog.setMethod(request.getMethod());
        operationLog.setRequestBody(passwrodReplace(StringEscapeUtils.unescapeJson(requestBody)));
        operationLog.setResponseBody(StringUtils.trim(responseBody));
        operationLog.setUserAgent(request.getHeader("user-agent"));

        // 使用线程异步保存日志
        new SaveLogThread(operationLog).start();
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread {

        private LoginLog loginLog;

        private OperationLog operationLog;

        public SaveLogThread(LoginLog loginLog) {
            super(SaveLogThread.class.getSimpleName());
            this.loginLog = loginLog;
            this.operationLog = null;
        }

        public SaveLogThread(OperationLog operationLog){
            super(SaveLogThread.class.getSimpleName());
            this.loginLog = null;
            this.operationLog = operationLog;
        }

        @Override
        public void run() {
            // 保存日志信息
            try {
                if (null != loginLog) { //登录日志
                    if (loginLog.getIsNewRecord()) {
                        loginLog.preInsert();
                        loginLogDao.insert(loginLog);
                    } else {
                        loginLog.preUpdate();
                        loginLogDao.update(loginLog);
                    }
                }
                if (null != operationLog) { //操作日志
                    operationLog.preInsert();
                    operationLogDao.insert(operationLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(HttpServletRequest request){
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        if (!StringUtils.equals(contextPath, "/")) {
            requestUri = StringUtils.remove(requestUri, contextPath);
        }

        @SuppressWarnings("unchecked")
        Map<String, String> menuMap = (Map<String, String>) CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
        if (menuMap == null){
            menuMap = Maps.newHashMap();
            List<Menu> menuList = menuDao.findAllList(new Menu());
            for (Menu menu : menuList){
                // 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
                String namePath = "";
                if (menu.getParentIds() != null){
                    List<String> namePathList = Lists.newArrayList();
                    for (String id : StringUtils.split(menu.getParentIds(), ",")){
                        Long lid = Long.parseLong(id);
                        if (menu.getRootId().equals(lid) || lid.equals(1L)){
                            continue; // 过滤跟节点
                        }
                        for (Menu m : menuList){
                            if (m.getId().equals(lid)){
                                namePathList.add(m.getName());
                                break;
                            }
                        }
                    }
                    namePathList.add(menu.getName());
                    namePath = StringUtils.join(namePathList, "-");
                }

                // 设置菜单名称路径
                if (StringUtils.isNotBlank(menu.getUri())){
                    menuMap.put(menu.getUri(), namePath);
                }
            }
            menuMap.put("/sys/user/updatePassword", "修改密码");
            CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
        }

        String menuNamePath = menuMap.get(requestUri);
        if (menuNamePath == null){
            return "";
        }
        return menuNamePath;
    }

    public static String passwrodReplace(String s) {
        String str = new String(s);
        String startStr = "password\":\"";
        String endStr = "\"";
        int pos = -1;
        while ((pos = StringUtils.indexOf(str.toLowerCase(), startStr, pos)) != -1) {
            pos += startStr.length();
            int endpos = StringUtils.indexOf(str.toLowerCase(), endStr, pos);
            if (endpos != -1) {
                str = StringUtils.substring(str, 0, pos) + StringUtils.repeat('*', endpos - pos) + StringUtils.substring(str, endpos);
                pos = endpos + 1;
            }
        }
        return str;
    }
}
