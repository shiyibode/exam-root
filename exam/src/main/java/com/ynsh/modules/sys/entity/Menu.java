package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.persistence.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 */
public class Menu extends TreeEntity<Menu, Long> {

    /**
     * 描述
     */
    private String description;

    private String type;    //类型
    private String uri; 	// URI
    private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
    private String iconCls; // 图标
    private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识

    private Long userId;

    private List<Menu> permissiveOpts;  //模块对当前登录用户许可的操作,即存放这个模块下授权的操作按钮(button)

    public Menu(){
        super();
    }

    public Menu(Long id){
        super(id);
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    @JsonIgnore
    public Long getRootId() {
        return 0L;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Length(min=0, max=255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min=0, max=2000)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Length(min=0, max=20)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min=0, max=100)
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Length(min=1, max=1)
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        if (isShow.equalsIgnoreCase("true")) {
            this.isShow = "1";
            return;
        }
        if (isShow.equalsIgnoreCase("false")) {
            this.isShow = "0";
            return;
        }
        this.isShow = isShow;
    }

    @Length(min=0, max=200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, Long parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            Menu e = sourcelist.get(i);
            if (e.getParentId()!=null && e.getParentId().equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        Menu child = sourcelist.get(j);
                        if (child.getParentId()!=null && child.getParentId().equals(e.getId())){
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Menu> getPermissiveOpts() {
        if (null == permissiveOpts) {
            permissiveOpts = new ArrayList<>();
        }
        return permissiveOpts;
    }

    public void setPermissiveOpts(List<Menu> permissiveOpts) {
        this.permissiveOpts = permissiveOpts;
    }

    @Override
    public String toString() {
        return name;
    }


}
