package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.ynsh.common.persistence.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 角色Entity
 */
public class Role extends DataEntity<Role, Long> {
    private Menu module;  // 归属机构
    private String name;    // 角色名称
    private String enname;    // 英文名称
    private String roleType;// 权限类型
    private Integer dataScope;// 数据范围

    private Boolean sysData;        //是否是系统数据
    private Boolean useable;        //是否是可用

    private User user;        // 根据用户ID查询角色列表

    private String oldName;    // 原角色名称
    private String oldEnname;    // 原英文名称

    //	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
    private List<Menu> menuList = Lists.newArrayList(); // 拥有菜单列表
    private List<Organization> organizationList = Lists.newArrayList(); // 按明细设置数据范围

    // 数据范围（1：所有数据；2：按明细设置；3：所在机构及以下数据；4：所在机构数据；5：仅本人数据）
    public static final Integer DATA_SCOPE_ALL = 1;
    public static final Integer DATA_SCOPE_CUSTOM = 2;
    public static final Integer DATA_SCOPE_ORGANIZATION_AND_CHILD = 3;
    public static final Integer DATA_SCOPE_ORGANIZATION = 4;
    public static final Integer DATA_SCOPE_SELF = 5;

    public static final String[] DATA_SCOPE_STR = {"未定义", "所有数据", "按明细设置", "所在机构及以下数据", "所在机构数据", "仅本人数据"};

    public Role() {
        super();
        this.useable = true;
    }

    public Role(Long id) {
        super(id);
    }

    public Role(User user) {
        this();
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public Boolean getSysData() {
        return sysData;
    }

    public void setSysData(Boolean sysData) {
        this.sysData = sysData;
    }

    public Menu getModule() {
        if (null == module) {
            module = new Menu();
        }
        return module;
    }

    public void setModule(Menu module) {
        this.module = module;
    }

    public Long getModuleId() {
        return this.getModule().getId();
    }

    public void setModuleId(Long moduleId) {
        this.getModule().setId(moduleId);
    }

    @Length(min=1, max=100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=1, max=100)
    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    @Length(min=1, max=100)
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
    }

    public String getDataScopeStr() {
        if (null != this.getDataScope()) {
            return DATA_SCOPE_STR[this.getDataScope()];
        }
        return DATA_SCOPE_STR[0];
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldEnname() {
        return oldEnname;
    }

    public void setOldEnname(String oldEnname) {
        this.oldEnname = oldEnname;
    }

    @JsonIgnore
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Long> getMenuIdList() {
        List<Long> menuIdList = Lists.newArrayList();
        for (Menu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuList = Lists.newArrayList();
        for (Long menuId : menuIdList) {
            Menu menu = new Menu();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = Lists.newArrayList();
        if (menuIds != null){
            String[] ids = StringUtils.split(menuIds, ",");
            Long[] lIds = new Long[ids.length];
            for (int i = 0; i < ids.length; i++) {
                lIds[i] = Long.parseLong(ids[i]);
            }
            setMenuIdList(Lists.newArrayList(lIds));
        }
    }

    @JsonIgnore
    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Long> getOrganizationIdList() {
        List<Long> organizationIdList = Lists.newArrayList();
        for (Organization organization : organizationList) {
            organizationIdList.add(organization.getId());
        }
        return organizationIdList;
    }

    public void setOrganizationIdList(List<Long> organizationIdList) {
        organizationList = Lists.newArrayList();
        for (Long organizationId : organizationIdList) {
            Organization organization = new Organization();
            organization.setId(organizationId);
            organizationList.add(organization);
        }
    }

    public String getOrganizationIds() {
        return StringUtils.join(getOrganizationIdList(), ",");
    }

    public void setOrganizationIds(String organizationIds) {
        organizationList = Lists.newArrayList();
        if (organizationIds != null){
            String[] ids = StringUtils.split(organizationIds, ",");
            Long[] lIds = new Long[ids.length];
            for (int i = 0; i < ids.length; i++) {
                lIds[i] = Long.parseLong(ids[i]);
            }
            setOrganizationIdList(Lists.newArrayList(lIds));
        }
    }

    /**
     * 获取权限字符串列表
     */
    public List<String> getPermissions() {
        List<String> permissions = Lists.newArrayList();
        for (Menu menu : menuList) {
            if (menu.getPermission()!=null && !"".equals(menu.getPermission())){
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
