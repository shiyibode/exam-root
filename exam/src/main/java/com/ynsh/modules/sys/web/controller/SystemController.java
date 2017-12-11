package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.BaseController;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.security.shiro.FormAuthenticationFilter;
import com.ynsh.modules.sys.security.shiro.SystemAuthorizingRealm.Principal;
import com.ynsh.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 系统信息Controller
 */
@Controller
@RequestMapping("${sysPath}")
public class SystemController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    /**
     * 如果用户已登录,则返回登录信息
     * 如果未登录则该请求不会先进入方法内部而是由shiro拦截,去执行登录逻辑,在登录逻辑执行失败后才会进入该方法内部
     */
    @RequestMapping(value = "/login")
    public @ResponseBody Object login(HttpServletRequest request, HttpServletResponse response) {

        logger.info("SystemController::doLogin() method called!!");

        ResponseJson responseJson = new ResponseJson();
        Map<String, Object> retData = new HashMap<>(2);

        Principal principal = UserUtils.getPrincipal();
        if (null != principal) {
            UserOrganization userOrganization = UserUtils.getUserOrganization();
            if (userOrganization.getUser().getRoleList().size() <= 0) {
                userOrganization.getUser().setRoleList(UserUtils.getRoleList());
            }
            userOrganization.getUser().setMenuList(UserUtils.getMenuTree());

            responseJson.setSuccess(true);
            responseJson.setMsg("您已经登录!");
            retData.put("loggedIn", true);
            retData.put("userInfo", userOrganization);
            responseJson.setData(retData);
        } else {
            String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
            if (StringUtils.isNotBlank(message)) { //登录逻辑执行失败,如输入错误的用户名或密码
                responseJson.setMsg(message);
            } else {
                responseJson.setMsg("您还没有登录,或登录已超时, 请重新登录!");
            }
            responseJson.setSuccess(false);
            retData.put("loggedIn", false);
            retData.put("userInfo", null);
            responseJson.setData(retData);
        }

        return responseJson;
    }


    /**
     * 登录成功后返回给前端的信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/success")
    public @ResponseBody Object loginsuccess(HttpServletRequest request, HttpServletResponse response) {
        ResponseJson responseJson = new ResponseJson();
        Map<String, Object> retData = new HashMap<>(2);

        Principal principal = UserUtils.getPrincipal();

        if (null != principal) {
            UserOrganization userOrganization = UserUtils.getUserOrganization();
            if (userOrganization.getUser().getRoleList().size() <= 0) {
                userOrganization.getUser().setRoleList(UserUtils.getRoleList());
            }
            userOrganization.getUser().setMenuList(UserUtils.getMenuTree());

            responseJson.setSuccess(true);
            responseJson.setMsg("登录成功!");
            retData.put("loggedIn", true);
            retData.put("userInfo", userOrganization);
            responseJson.setData(retData);
        } else {
            responseJson.setSuccess(false);
            responseJson.setMsg("登录失败!");
            retData.put("loggedIn", false);
            retData.put("userInfo", null);
            responseJson.setData(retData);
        }

        return responseJson;
    }

    @RequestMapping(value = "/unauthorized")
    public @ResponseBody Object unauthorized(HttpServletRequest request, HttpServletResponse response) {
        ResponseJson responseJson = new ResponseJson();
        responseJson.setSuccess(false);
        responseJson.setMsg("您无权限访问该资源!");
        return responseJson;
    }
}
