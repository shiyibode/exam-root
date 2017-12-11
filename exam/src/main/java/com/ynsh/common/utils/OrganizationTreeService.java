package com.ynsh.common.utils;

import com.ynsh.common.core.persistence.entity.utils.TreeEntityUtils;
import com.ynsh.modules.sys.dao.OrganizationDao;
import com.ynsh.modules.sys.entity.Organization;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.DataScopeUtils;
import com.ynsh.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syb on 2017/9/9.
 */
public class OrganizationTreeService {

    private static OrganizationDao organizationDao = SpringContextHolder.getBean(OrganizationDao.class);
    /**
     * 获取当前用户在moduleId模块有权限访问的机构树
     * @return
     */
    public static List<Organization> getOrganizationTree(Long moduleId){
        List<Organization> organizationTree = new ArrayList<>();
        //1.获取当前用户有权限操作的机构
        List<Organization> organizationList = getOrganizationList(moduleId);
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
            List<Organization> unionOrganizationList = (List<Organization>) CollectionUtils.union(parentOrganizationList, organizationList);
            organizationTree = TreeEntityUtils.listToTree((List)unionOrganizationList);
        }

        return organizationTree;
    }

    /**
     * 获取当前用户在moduleId模块有权限访问的机构
     * @return
     */
    public static List<Organization> getOrganizationList(Long moduleId){
        @SuppressWarnings("unchecked")
        List<Organization> organizationList = new ArrayList<>();
        UserOrganization userOrganization = UserUtils.getUserOrganization();
        if (userOrganization != null && userOrganization.getUser() != null) {
            if (userOrganization.getUser().isAdmin()) {
                organizationList = organizationDao.findAllList(new Organization());
            } else {
                Organization organization = new Organization();
                organization.getSqlMap().put("dsf", DataScopeUtils.dataScopeFilter(moduleId,userOrganization, "a", ""));
                organizationList = organizationDao.findList(organization);
            }
        }
        return organizationList;
    }

}
