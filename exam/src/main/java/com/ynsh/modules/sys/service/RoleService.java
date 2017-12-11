package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.sys.dao.RoleDao;
import com.ynsh.modules.sys.dao.UserOrganizationDao;
import com.ynsh.modules.sys.entity.Role;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenjianjun on 2016/10/14.
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<RoleDao, Role, Long> {

    @Autowired
    private UserOrganizationDao userOrganizationDao;

    @Override
    @Transactional(readOnly = false)
    public void save(Role entity) {
        logger.info("menuIdList: {}", entity.getMenuIdList());
        super.save(entity);
        //更新角色与菜单关联
        if (entity.getMenuList().size() > 0) {
            dao.deleteRoleMenu(entity);
            dao.insertRoleMenu(entity);
        }
        //更新角色与部门关联
        if (entity.getDataScope() != null) {
            dao.deleteRoleOrganization(entity);
            if (entity.getDataScope().equals(Role.DATA_SCOPE_CUSTOM) && entity.getOrganizationList().size() > 0) {
                dao.insertRoleOrganization(entity);
            }
        }

        //清除当前用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);//shiro需要授权时，查找缓存没有权限信息，将会再次调用realm的doGetAuthorizationInfo去load用户的权限，再次放入缓存中

        //如果修改了角色,则清除拥有该角色的所有用户缓存
        if (!entity.getIsNewRecord()) {
            List<UserOrganization> userList = userOrganizationDao.findUserListByRoleId(entity.getId());
            for (UserOrganization uo : userList) {
                UserUtils.clearCache(uo);
            }
        }
    }


    @Override
    @Transactional(readOnly = false)
    public void delete(Role entity) {
        //删除角色与菜单关联
        dao.deleteRoleMenu(entity);

        //删除角色与部门关联
        dao.deleteRoleOrganization(entity);

        //删除角色
        super.delete(entity);

        //清除当前用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);

        //如果修改了角色,则清除拥有该角色的所有用户缓存
        if (!entity.getIsNewRecord()) {
            List<UserOrganization> userList = userOrganizationDao.findUserListByRoleId(entity.getId());
            for (UserOrganization uo : userList) {
                UserUtils.clearCache(uo);
            }
        }
    }
}
