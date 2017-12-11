package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.controller.TreeController;
import com.ynsh.modules.sys.entity.Organization;
import com.ynsh.modules.sys.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenjianjun on 16/8/4.
 */
@Controller
@RequestMapping("${sysPath}/organization")
public class OrganizationController extends TreeController<OrganizationService, Organization, Long> {
    @Override
    @RequiresPermissions(value = "sys:organization:get")
    public Object get(HttpServletRequest request, @RequestBody RequestJson<Organization, Long> requestJson) {
        return super.get(request, requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:organization:create")
    public Object create(@RequestBody RequestJson<Organization, Long> requestJson) {
        return super.create(requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:organization:update")
    public Object update(@RequestBody RequestJson<Organization, Long> requestJson) {
        return super.update(requestJson);
    }

    @Override
    @RequiresPermissions(value = "sys:organization:delete")
    public Object delete(@RequestBody RequestJson<Organization, Long> requestJson) {
        return super.delete(requestJson);
    }
}
