package com.ynsh.modules.sys.service;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.entity.utils.TreeEntityUtils;
import com.ynsh.common.core.service.TreeService;
import com.ynsh.common.utils.CacheUtils;
import com.ynsh.modules.sys.dao.MenuDao;
import com.ynsh.modules.sys.entity.Menu;
import com.ynsh.modules.sys.utils.UserUtils;
import com.ynsh.modules.sys.utils.WriteLogUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenjianjun on 16/3/24.
 */
@Service
@Transactional(readOnly = true)
public class MenuService extends TreeService<MenuDao, Menu, Long> {

    /**
     * 获取当前登录用户已授权的模块
     * @return
     * @throws Exception
     */
    public List<Menu> getModules() throws Exception {
        List<Menu> menuList = UserUtils.getMenuList();
        List<Menu> moduleList = new ArrayList<>();
        for (Menu menu: menuList) {
            if (menu.getType().equalsIgnoreCase("menu_group") && menu.getIsShow().equalsIgnoreCase(Global.SHOW)) {
                moduleList.add(menu);
            }
        }
        return moduleList;
    }

    /**
     * 该方法用于返回前端UI左侧模块功能菜单
     * @param id 模块菜单的根ID
     * @return
     */
    public List<Menu> getGroupMenuTree(Long id) {
        List<Menu> menuList = UserUtils.getMenuList();
        List<Menu> treeList = new ArrayList<>();

        for (Menu menu : menuList) {
            if (menu.getParentId().equals(id) && menu.getIsShow().equals(Global.SHOW)) {   //是否是模块下面节点的根节点
                treeList.add(menu);
            } else {
                continue;
            }
        }
        for (Menu menu : treeList) {
            getMenuChildren(menu, menuList);
        }

        return treeList;
    }

    private void getMenuChildren(Menu menu, List<Menu> menuList) {
        if (null == menu) {
            return;
        }
        menu.getChildren().clear();
        menu.getPermissiveOpts().clear();
        for (Menu child : menuList) {
            if (child.getParentId().equals(menu.getId())) {
                if (child.getType().equals("menu") && child.getIsShow().equals(Global.SHOW)) {
                    menu.getChildren().add(child);
                } else if (child.getType().equals("button") && child.getIsShow().equals(Global.SHOW)) {
                    menu.getPermissiveOpts().add(child);
                }
                getMenuChildren(child, menuList);
            }
        }
    }


    @Override
    public List<Menu> findAsTree(Menu menu) {
        List<Menu> menuList = UserUtils.getMenuList();
        return TreeEntityUtils.listToTree((List)menuList);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Menu entity) {
        super.save(entity);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        CacheUtils.remove(WriteLogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(List<Menu> entities) {
        super.save(entities);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        CacheUtils.remove(WriteLogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Menu entity) {
        super.delete(entity);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        CacheUtils.remove(WriteLogUtils.CACHE_MENU_NAME_PATH_MAP);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(List<Menu> entities) {
        super.delete(entities);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        CacheUtils.remove(WriteLogUtils.CACHE_MENU_NAME_PATH_MAP);
    }
}
