package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.TreeController;
import com.ynsh.modules.sys.entity.Menu;
import com.ynsh.modules.sys.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenjianjun on 16/3/28.
 */
@Controller
@RequestMapping("${sysPath}/menu")
public class MenuController extends TreeController<MenuService, Menu, Long> {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 获取所有菜单分组(按系统模块划分),用于前端UI对菜单按模块分组
     * @return
     */
    @RequestMapping("/menugroups")
    @RequiresPermissions(value = "sys:menu:get")
    public  @ResponseBody Object menuGroups() {
        ResponseJson responseJson = new ResponseJson();

        try {
            List<Menu> modules = service.getModules();
            responseJson.setData(modules);
            responseJson.setSuccess(true);
            responseJson.setTotal(Long.valueOf(modules.size()));
            responseJson.setMsg("");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            responseJson.setSuccess(false);
            responseJson.setTotal(0L);
            responseJson.setMsg("后台错误, 获取数据时出错!");
        }

        return responseJson;
    }

    /**
     * 获取某一菜单分组对应的所有菜单项,其中buttons封装成
     * @param requestJson
     * @return
     */
    @RequestMapping(value = "/groupmenus")
    @RequiresPermissions(value = "sys:menu:get")
    public @ResponseBody Object groupMenus(@RequestBody RequestJson<Menu, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        try {
            List<Menu> groupMenuTree = service.getGroupMenuTree(requestJson.getId());
            responseJson.setSuccess(true);
            responseJson.setMsg("");
            responseJson.setTotal(1L);
            responseJson.setChildren(groupMenuTree);
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setMsg("后台错误, 获取数据时出错!");
            responseJson.setTotal(0L);
        }


        return responseJson;
    }


    @Override
    @RequiresPermissions(value = "sys:menu:get")
    public Object get(HttpServletRequest request, @RequestBody RequestJson<Menu, Long> requestJson) {
        return super.get(request, requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:menu:create")
    public Object create(@RequestBody RequestJson<Menu, Long> requestJson) {
        return super.create(requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:menu:update")
    public Object update(@RequestBody RequestJson<Menu, Long> requestJson) {
        return super.update(requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:menu:delete")
    public Object delete(@RequestBody RequestJson<Menu, Long> requestJson) {
        return super.delete(requestJson);
    }
}
