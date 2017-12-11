package com.ynsh.modules.sys.utils;

import com.google.common.collect.Lists;
import com.ynsh.common.core.persistence.entity.BaseEntity;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.entity.Role;
import com.ynsh.modules.sys.entity.UserOrganization;

import java.util.List;

/**
 * 数据范围过滤工具类
 */
public class DataScopeUtils {
    /**
     * 数据范围过滤, 限定数据范围
     * @param moduleId 模块ID
     * @param minDataScope 如果为true，则从所有角色列表里选择数据范围最小的那个角色
     * @param userOrganization 当前用户对象，通过“entity.getCurrentUserOrganization()”获取
     * @param organizationAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(Long moduleId, boolean minDataScope, UserOrganization userOrganization, String organizationAlias, String userAlias) {

        if (!minDataScope) {
            return dataScopeFilter(moduleId, userOrganization, organizationAlias, userAlias);
        }

        StringBuilder sqlString = new StringBuilder();

        // 超级管理员，跳过权限过滤
        if (!userOrganization.getUser().isAdmin()) {
            boolean isDataScopeAll = false;
            Role r = null;

            for (Role role : userOrganization.getUser().getRoleList()) {
                if (!role.getModuleId().equals(moduleId)) {
                    continue;
                }
                if (null == r) {
                    r = role;
                } else {
                    if (r.getDataScope().intValue() < role.getDataScope().intValue()) {
                        r = role;
                    }
                }
            }
            if (null != r) {
                for (String oa : StringUtils.split(organizationAlias, ",")){
                    if (Role.DATA_SCOPE_ALL.equals(r.getDataScope())){
                        isDataScopeAll = true;
                    }
                    else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
//							String organizationIds =  StringUtils.join(r.getOrganizationIdList(), "','");
//							if (StringUtils.isNotEmpty(organizationIds)){
//								sqlString.append(" OR " + oa + ".id IN ('" + organizationIds + "')");
//							}
                        sqlString.append(" OR EXISTS (SELECT 1 FROM t_sys_role_organization WHERE role_id = " + r.getId());
                        sqlString.append(" AND organization_id = " + oa +".id)");
                    }
                    else if (Role.DATA_SCOPE_ORGANIZATION_AND_CHILD.equals(r.getDataScope())){
                        String parentIds = userOrganization.getOrganization().getParentIds() + "," + userOrganization.getOrganization().getId();
                        sqlString.append(" OR " + oa + ".id = " + userOrganization.getOrganization().getId());
                        sqlString.append(" OR " + oa + ".parent_ids @> CAST(string_to_array('" + parentIds + "', ',') AS INTEGER[])");
                    }
                    else if (Role.DATA_SCOPE_ORGANIZATION.equals(r.getDataScope())){
                        sqlString.append(" OR " + oa + ".id = " + userOrganization.getOrganization().getId());
                    }
                    //else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
                }
            }
            // 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
            if (!isDataScopeAll) {
                if (StringUtils.isNotBlank(userAlias)) {
                    for (String ua : StringUtils.split(userAlias, ",")) {
                        sqlString.append(" OR " + ua + ".id = " + userOrganization.getUser().getId());
                    }
                } else {
                    for (String oa : StringUtils.split(organizationAlias, ",")) {
                        //sqlString.append(" OR " + oa + ".id  = " + user.getOrganization().getId());
                        sqlString.append(" OR " + oa + ".id IS NULL");
                    }
                }
            } else {
                // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
                sqlString = new StringBuilder();
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())) {
            return " AND (" + sqlString.substring(4) + ")";
        }
        return "";
    }

    /**
     * 数据范围过滤
     * @param moduleId 模块ID
     * @param userOrganization 当前用户对象，通过“entity.getCurrentUserOrganization()”获取
     * @param organizationAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(Long moduleId, UserOrganization userOrganization, String organizationAlias, String userAlias) {

        StringBuilder sqlString = new StringBuilder();

        // 进行权限过滤，多个角色权限范围之间为或者关系。
        List<Integer> dataScope = Lists.newArrayList();

        // 超级管理员，跳过权限过滤
        if (!userOrganization.getUser().isAdmin()){
            boolean isDataScopeAll = false;
            for (Role r : userOrganization.getUser().getRoleList()){
                if (!r.getModuleId().equals(moduleId)) {
                    continue;
                }
                for (String oa : StringUtils.split(organizationAlias, ",")){
                    if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
                        if (Role.DATA_SCOPE_ALL.equals(r.getDataScope())){
                            isDataScopeAll = true;
                        }
                        else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
//							String organizationIds =  StringUtils.join(r.getOrganizationIdList(), "','");
//							if (StringUtils.isNotEmpty(organizationIds)){
//								sqlString.append(" OR " + oa + ".id IN ('" + organizationIds + "')");
//							}
                            sqlString.append(" OR EXISTS (SELECT 1 FROM t_sys_role_organization WHERE role_id = " + r.getId());
                            sqlString.append(" AND organization_id = " + oa +".id)");
                        }
                        else if (Role.DATA_SCOPE_ORGANIZATION_AND_CHILD.equals(r.getDataScope())){
                            String parentIds = userOrganization.getOrganization().getParentIds() + "," + userOrganization.getOrganization().getId();
                            sqlString.append(" OR " + oa + ".id = " + userOrganization.getOrganization().getId());
                            sqlString.append(" OR " + oa + ".parent_ids @> CAST(string_to_array('" + parentIds + "', ',') AS INTEGER[])");
                        }
                        else if (Role.DATA_SCOPE_ORGANIZATION.equals(r.getDataScope())){
                            sqlString.append(" OR " + oa + ".id = " + userOrganization.getOrganization().getId());
                        }
                        //else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
                    }
                }
                dataScope.add(r.getDataScope());
            }
            // 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
            if (!isDataScopeAll){
                if (StringUtils.isNotBlank(userAlias)){
                    for (String ua : StringUtils.split(userAlias, ",")){
                        sqlString.append(" OR " + ua + ".id = " + userOrganization.getUser().getId());
                    }
                }else {
                    for (String oa : StringUtils.split(organizationAlias, ",")){
                        //sqlString.append(" OR " + oa + ".id  = " + user.getOrganization().getId());
                        sqlString.append(" OR " + oa + ".id IS NULL");
                    }
                }
            }else{
                // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
                sqlString = new StringBuilder();
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())){
            return " AND (" + sqlString.substring(4) + ")";
        }
        return "";
    }

    /**
     * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
     * @param moduleId 模块ID
     * @param entity 当前过滤的实体类
     * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
     * @param organizationWheres organization表条件，组成：部门表字段=业务表的部门字段
     * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
     * @example
     * 		dataScopeFilter(user, "dsf", "id=a.organization_id", "id=a.create_by");
     * 		dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
     */
    public static void dataScopeFilter(Long moduleId, BaseEntity<?, ?> entity, String sqlMapKey, String organizationWheres, String userWheres) {

//        User user = entity.getCurrentUserOrganization();
        UserOrganization userOrganization = UserUtils.getUserOrganization();

        // 如果是超级管理员，则不过滤数据
        if (userOrganization.getUser().isAdmin()) {
            return;
        }

        // 数据范围（1：所有数据；2：按明细设置；3：所在机构及以下数据；4：所在机构数据；5：仅本人数据；）
        StringBuilder sqlString = new StringBuilder();

        // 获取到最大的数据权限范围
        Long roleId = null;
        int dataScopeInteger = Role.DATA_SCOPE_SELF;
        for (Role r : userOrganization.getUser().getRoleList()){
            if (!r.getModuleId().equals(moduleId)) {
                continue;
            }
            int ds = Integer.valueOf(r.getDataScope());
            if (ds == Role.DATA_SCOPE_CUSTOM){
                roleId = r.getId();
                dataScopeInteger = ds;
                break;
            }else if (ds < dataScopeInteger){
                roleId = r.getId();
                dataScopeInteger = ds;
            }
        }
        String dataScopeString = String.valueOf(dataScopeInteger);

        // 生成部门权限SQL语句
        for (String where : StringUtils.split(organizationWheres, ",")){
            if (Role.DATA_SCOPE_ORGANIZATION_AND_CHILD.equals(dataScopeString)){
                String parentIds = userOrganization.getOrganization().getParentIds() + "," + userOrganization.getOrganization().getId();
                sqlString.append(" AND EXISTS (SELECT 1 FROM t_sys_organization");
                sqlString.append(" WHERE (id = " + userOrganization.getOrganization().getId());
                sqlString.append(" OR parent_ids @> CAST(string_to_array('" + parentIds + "', ',') AS INTEGER[]) )");
                sqlString.append(" AND " + where +")");
            }
            else if (Role.DATA_SCOPE_ORGANIZATION.equals(dataScopeString)){
                sqlString.append(" AND EXISTS (SELECT 1 FROM t_sys_organization");
                sqlString.append(" WHERE id = " + userOrganization.getOrganization().getId());
                sqlString.append(" AND " + where +")");
            }
            else if (Role.DATA_SCOPE_CUSTOM.equals(dataScopeString)){
                sqlString.append(" AND EXISTS (SELECT 1 FROM t_sys_role_organization ro123456, t_sys_organization o123456");
                sqlString.append(" WHERE ro123456.organization_id = o123456.id");
                sqlString.append(" AND ro123456.role_id = " + roleId);
                sqlString.append(" AND o123456." + where +")");
            }
        }
        // 生成个人权限SQL语句
        for (String where : StringUtils.split(userWheres, ",")){
            if (Role.DATA_SCOPE_SELF.equals(dataScopeString)){
                sqlString.append(" AND EXISTS (SELECT 1 FROM t_sys_user");
                sqlString.append(" WHERE id=" + userOrganization.getUser().getId());
                sqlString.append(" AND " + where + ")");
            }
        }

//		System.out.println("dataScopeFilter: " + sqlString.toString());

        // 设置到自定义SQL对象
        entity.getSqlMap().put(sqlMapKey, sqlString.toString());

    }
}
