package com.ynsh.modules.sys.utils;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.entity.utils.TreeEntityUtils;
import com.ynsh.common.core.web.servlet.Servlets;
import com.ynsh.common.utils.CacheUtils;
import com.ynsh.common.utils.SpringContextHolder;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.dao.*;
import com.ynsh.modules.sys.entity.*;
import com.ynsh.modules.sys.security.shiro.SystemAuthorizingRealm.Principal;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 用户工具类,主要针对登录用的缓存设置
 */
public class UserUtils {

    private static UserOrganizationDao userOrganizationDao = SpringContextHolder.getBean(UserOrganizationDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OrganizationDao organizationDao = SpringContextHolder.getBean(OrganizationDao.class);

    //CacheUtils(EhCache) 主要用于用户登录时使用
    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_USER_ID_ = "uid_";
    public static final String USER_CACHE_USER_CODE_ = "uc";


    //登录用户在自己session里缓存的对象,即当前登录用户有权限访问的数据
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_ORGANIZATION_LIST = "organizationList";
    public static final String CACHE_ORGANIZATION_TREE = "organizationTree";

    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    /**
     * 根据用户ID获取用户(当前在职机构)
     * @param userId
     * @return 取不到返回null
     */
    public static UserOrganization getUserOrganizationByUserId(Long userId){
        UserOrganization userOrganization = (UserOrganization) CacheUtils.get(USER_CACHE, USER_CACHE_USER_ID_ + userId);
        if (userOrganization ==  null){
            userOrganization = userOrganizationDao.getByUserId(userId);
            if (userOrganization != null && userOrganization.getUser() != null) {
                //user.setRoleList(roleDao.findList(new Role(user)));
                CacheUtils.put(USER_CACHE, USER_CACHE_USER_ID_ + userOrganization.getUser().getId(), userOrganization);
                CacheUtils.put(USER_CACHE, USER_CACHE_USER_CODE_ + userOrganization.getUser().getCode(), userOrganization);
            }
        }
        return userOrganization;
    }

    /**
     * 根据用户编号/柜员号获取用户(当前在职机构),
     * @param code
     * @return 取不到返回null
     */
    public static UserOrganization getUserOrganizationByUserCode(String code){
        UserOrganization userOrganization = (UserOrganization) CacheUtils.get(USER_CACHE, USER_CACHE_USER_CODE_ + code);
        if (userOrganization == null){
            userOrganization = userOrganizationDao.getByUserCode(code);
            if (userOrganization != null && userOrganization.getUser() != null) {
                //user.setRoleList(roleDao.findList(new Role(user)));
                CacheUtils.put(USER_CACHE, USER_CACHE_USER_ID_ + userOrganization.getUser().getId(), userOrganization);
                CacheUtils.put(USER_CACHE, USER_CACHE_USER_CODE_ + userOrganization.getUser().getCode(), userOrganization);
            }
        }
        return userOrganization;
    }

    /**
     * 根据用户ID获取用户(当前在职机构)
     * @param userId
     * @return 取不到返回null
     */
    public static User getUserByUserId(Long userId){
        UserOrganization userOrganization = getUserOrganizationByUserId(userId);
        if (userOrganization != null) {
            return userOrganization.getUser();
        }
        return null;
    }

    /**
     * 根据用户编号/柜员号获取用户(当前在职机构),
     * @param code
     * @return 取不到返回null
     */
    public static User getUserByUserCode(String code){
        UserOrganization userOrganization = getUserOrganizationByUserCode(code);
        if (userOrganization != null) {
            return userOrganization.getUser();
        }
        return null;
    }


    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_ORGANIZATION_LIST);
        removeCache(CACHE_ORGANIZATION_TREE);
        UserUtils.clearCache(getUserOrganization());
    }

    /**
     * 清除指定用户缓存
     * @param userOrganization
     */
    public static void clearCache(UserOrganization userOrganization){
        CacheUtils.remove(USER_CACHE, USER_CACHE_USER_ID_ + userOrganization.getUser().getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_USER_CODE_ + userOrganization.getUser().getCode());
        CacheUtils.remove(USER_CACHE, USER_CACHE_USER_CODE_ + userOrganization.getUser().getOldCode());
    }

    /**
     * 获取当前用户信息
     * @return 取不到返回 new User()
     */
    public static User getUser(){
        Principal principal = getPrincipal();
        if (principal!=null){
            User user = getUserByUserId(principal.getId());
            if (user != null){
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的UserOrganization对象。
        return new User();
    }

    /**
     * 获取当前用户信息与机构信息
     * @return 取不到返回 new User()
     */
    public static UserOrganization getUserOrganization(){
        Principal principal = getPrincipal();
        if (principal!=null){
            UserOrganization userOrganization = getUserOrganizationByUserId(principal.getId());
            if (userOrganization != null){
                return userOrganization;
            }
            return new UserOrganization();
        }
        // 如果没有登录，则返回实例化空的UserOrganization对象。
        return new UserOrganization();
    }


    /**
     * 获取当前用户角色列表
     * @return
     */
    public static List<Role> getRoleList(){
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
        if (roleList == null){
            UserOrganization userOrganization = getUserOrganization();
            if (userOrganization != null && userOrganization.getUser() != null) {
                if (userOrganization.getUser().isAdmin()) {
                    roleList = roleDao.findAllList(new Role());
                } else {
                    Role role = new Role();
                    role.getSqlMap().put("dsf", DataScopeUtils.dataScopeFilter(Global.getSysModuleId(), userOrganization, "o", "u"));
                    roleList = roleDao.findList(role);
                }
                putCache(CACHE_ROLE_LIST, roleList);
            }
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<Menu> getMenuList(){
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
        if (menuList == null){
            User user = getUser();
            if (user != null) {
                if (user.isAdmin()) {
                    menuList = menuDao.findAllList(new Menu());
                } else {
                    Menu m = new Menu();
                    m.setUserId(user.getId());
                    menuList = menuDao.findByUserId(m);
                }
                putCache(CACHE_MENU_LIST, menuList);
            }
        }
        return menuList;
    }

    /**
     * 获取当前用户用户授权菜单树
     * @return
     */
    public static List<Menu> getMenuTree(){
        //1.获取当前用户有权限的菜单列表
        List<Menu> menuList = getMenuList();
        //转成树结构
        List<Menu> menuTree = TreeEntityUtils.listToTree((List) menuList);
        //如果用户没有全部菜单权限,则让其能看到上层菜单结构
        List<Menu> parentMenuList = new ArrayList<>();
        for (Menu menu : menuTree) {
            if (menu.getParentId().equals(menu.getRootId())) {
                continue;
            } else {
                String[] parentIds = menu.getParentIds().split(",");
                for (String parentId : parentIds) {
                    //如果parenId 没有在parentOrganizationList找到, 则从数据库中获取并添加到 parentOrganizationList
                    if (!parentMenuList.contains(new Menu(Long.valueOf(parentId)))) {
                        Menu parent = menuDao.get(Long.valueOf(parentId));
                        if (parent != null) {
                            //设置用户无权限修改,用于前端控制
                            parent.setEditable(false);
                            parentMenuList.add(parent);
                        }
                    }
                }
            }
        }
        if (parentMenuList.size() > 0) {
            //合并
            List<Menu> unionMenuList = (List<Menu>)CollectionUtils.union(parentMenuList, menuList);
            menuTree = TreeEntityUtils.listToTree((List)unionMenuList);
        }
        return menuTree;
    }


    /**
     * 获取当前用户授权的区域
     * @return
     */
    public static List<Area> getAreaList(){
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
        if (areaList == null){
            areaList = areaDao.findAllList(new Area());
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的机构
     * @return
     */
    public static List<Organization> getOrganizationList(){
        @SuppressWarnings("unchecked")
        List<Organization> organizationList = (List<Organization>)getCache(CACHE_ORGANIZATION_LIST);
        if (organizationList == null){
            UserOrganization userOrganization = getUserOrganization();
            if (userOrganization != null && userOrganization.getUser() != null) {
                if (userOrganization.getUser().isAdmin()) {
                    organizationList = organizationDao.findAllList(new Organization());
                } else {
                    Organization organization = new Organization();
                    organization.getSqlMap().put("dsf", DataScopeUtils.dataScopeFilter(Global.getSysModuleId(),userOrganization, "a", ""));
                    organizationList = organizationDao.findList(organization);
                }
                putCache(CACHE_ORGANIZATION_LIST, organizationList);
            }
        }
        return organizationList;
    }

    /**
     * 获取当前用户有权限访问的机构树
     * @return
     */
    public static List<Organization> getOrganizationTree(){
        @SuppressWarnings("unchecked")
        List<Organization> organizationTree = (List<Organization>)getCache(CACHE_ORGANIZATION_TREE);
        if (organizationTree == null) {
            //1.获取当前用户有权限操作的机构
            List<Organization> organizationList = getOrganizationList();

            //转成树结构
            organizationTree = TreeEntityUtils.listToTree((List) organizationList);

            //如果用户没有全部机构权限,则让其能看到上层机构结构
            List<Organization> parentOrganizationList = new ArrayList<>();
            for (Organization organization : organizationTree) {
                if (organization.getParentId().equals(organization.getRootId())) {
                    continue;
                } else {
                    String[] parentIds = organization.getParentIds().split(",");
                    for (String parentId : parentIds) {
                        //如果parenId 没有在parentOrganizationList找到, 则从数据库中获取并添加到 parentOrganizationList
                        if (!parentOrganizationList.contains(new Organization(Long.valueOf(parentId)))) {
                            Organization parent = organizationDao.get(Long.valueOf(parentId));
                            if (parent != null) {
                                //设置用户无权限修改,用于前端控制
                                parent.setEditable(false);
                                parentOrganizationList.add(parent);
                            }
                        }
                    }
                }
            }
            if (parentOrganizationList.size() > 0) {
                //合并
                List<Organization> unionOrganizationList = (List<Organization>)CollectionUtils.union(parentOrganizationList, organizationList);
                organizationTree = TreeEntityUtils.listToTree((List)unionOrganizationList);
            }
            putCache(CACHE_ORGANIZATION_TREE, organizationTree);
        }
        return organizationTree;
    }


    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
//			subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
//			subject.logout();
        }catch (InvalidSessionException e){

        }
        return null;
    }


    //更新用户登录信息
    public static void updateUserLoginInfo() {
        User user = getUser();
        if (null != user) {
            // 保存上次登录信息
            user.setOldLoginIp(user.getLoginIp());
            user.setOldLoginTime(user.getLoginTime());
            // 更新本次登录信息
            user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
            user.setLoginTime(new Date());
            userOrganizationDao.updateUserLoginInfo(user);
        }
    }


// ============== User Session Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

}