package com.ynsh.modules.sys.security.shiro;

import com.ynsh.common.config.Global;
import com.ynsh.common.utils.Encodes;
import com.ynsh.common.utils.PasswordUtils;
import com.ynsh.common.utils.SpringContextHolder;
import com.ynsh.modules.sys.entity.Menu;
import com.ynsh.modules.sys.entity.Role;
import com.ynsh.modules.sys.entity.User;
import com.ynsh.modules.sys.service.SessionService;
import com.ynsh.modules.sys.service.UserService;
import com.ynsh.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统安全认证实现类
 */
@Service
@DependsOn({"userOrganizationDao","roleDao","menuDao"})
public class SystemAuthorizingRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;

    private SessionService sessionService;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
//        logger.info("登录验证1  !!!!");

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;


        int activeSessionSize = getSessionService().getActiveSessions(false).size();
        if (logger.isDebugEnabled()){
            logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
        }

        // 校验用户名密码
        User user = getUserService().getUserByUserCode(token.getUsername());
        if (user != null) {
            if (!user.getLoginUseable()){
                throw new AuthenticationException("msg:该帐号已禁止登录.");
            }
            byte[] salt = Encodes.decodeHex(user.getLoginPassword().substring(0,16));
            return new SimpleAuthenticationInfo(new Principal(user), user.getLoginPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限验证  !!!!!!");
        Principal principal = (Principal) getAvailablePrincipal(principals);
        // 获取当前已登录的用户
        if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
            Collection<Session> sessions = getSessionService().getActiveSessions(true, principal, UserUtils.getSession());
            if (sessions.size() > 0){
                // 如果是登录进来的，则踢出已在线用户
                if (UserUtils.getSubject().isAuthenticated()){
                    for (Session session : sessions){
                        session.setAttribute("logoutTime", Long.valueOf(System.currentTimeMillis()));
                        session.setAttribute("logoutType", "系统踢出");
                        getSessionService().delete(session);
                    }
                }
                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                else{
                    Subject subject = UserUtils.getSubject();
                    Session session = subject.getSession();
                    session.setAttribute("logoutTime", Long.valueOf(System.currentTimeMillis()));
                    session.setAttribute("logoutType", "系统踢出");
                    subject.logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }

        User user = getUserService().getUserByUserCode(principal.getCode());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Menu> list = UserUtils.getMenuList();
            for (Menu menu : list){
                if (StringUtils.isNotBlank(menu.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(),",")){
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (Role role : user.getRoleList()){
                info.addRole(role.getEnname());
            }
            // 更新登录IP和时间
            //getUserService().updateUserLoginInfo(user);
            // 记录登录日志
            //LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        } else {
            return null;
        }
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }



    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     * @param permission
     */
    private void authorizationValidate(Permission permission){
        // 模块授权预留接口
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PasswordUtils.HASH_ALGORITHM);
        matcher.setHashIterations(PasswordUtils.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getText());
//		clearCachedAuthorizationInfo(principals);
//	}

    /**
     * 清空所有关联认证
     * @Deprecated 不需要清空，授权缓存保存到session中
     */
    @Deprecated
    public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
    }

    /**
     * 获取用户业务对象
     */
    public UserService getUserService() {
        if (userService == null){
            userService = SpringContextHolder.getBean(UserService.class);
        }
        return userService;
    }

    /**
     * 获取Session业务对象
     * @return
     */
    public SessionService getSessionService() {
        if (sessionService == null) {
            sessionService = SpringContextHolder.getBean(SessionService.class);
        }
        return sessionService;
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id; // 编号
        private String code; // 登录名,柜员号
        private String name; // 姓名

//		private Map<String, Object> cacheMap;

        public Principal(User user) {
            this.id = user.getId();
            this.code = user.getCode();
            this.name = user.getName();
        }

        public Long getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try{
                return (String) UserUtils.getSession().getId();
            }catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id.toString();
        }

    }
}
