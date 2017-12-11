package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.service.ServiceException;
import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenjianjun on 16/8/4.
 */
@Controller
@RequestMapping("${sysPath}/user")
public class UserController extends CrudController<UserService, UserOrganization, Long> {

    @RequestMapping("/updatePassword")
    public @ResponseBody Object updatePassword(@RequestBody RequestJson<UserOrganization, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        service.updatePassword(requestJson.getEntities());

        responseJson.setSuccess(true);
        responseJson.setMsg("修改密码成功!");
        return responseJson;
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("/resetPassword")
    public @ResponseBody Object resetPassword(@RequestBody RequestJson<UserOrganization, Long> requestJson) {

        ResponseJson responseJson = new ResponseJson();

        service.resetPassword(requestJson.getEntities());

        responseJson.setSuccess(true);
        responseJson.setMsg("密码重置成功!");
        return responseJson;
    }

    @RequestMapping("/updateOrganization")
    public @ResponseBody Object updateOrganization(@RequestBody RequestJson<UserOrganization, Long> requestJson) throws ServiceException {
        ResponseJson responseJson = new ResponseJson();

        service.alterOrganization(requestJson.getEntities());

        responseJson.setSuccess(true);
        responseJson.setMsg("用户机构调入/注销成功!");
        return responseJson;
    }

    @RequestMapping("/updateRole")
    public @ResponseBody Object updateRole(@RequestBody RequestJson<UserOrganization, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        service.alterRole(requestJson.getEntities());

        responseJson.setSuccess(true);
        responseJson.setMsg("用户角色修改成功!");
        return responseJson;
    }
}
