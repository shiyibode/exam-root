package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.BaseController;
import com.ynsh.modules.sys.entity.DateTree;
import com.ynsh.modules.sys.entity.LoginLog;
import com.ynsh.modules.sys.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by chenjianjun on 2016/10/24.
 */
@Controller
@RequestMapping("${sysPath}/loginlog")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(value = "/dateTree")
    public @ResponseBody Object dataTree(@RequestBody RequestJson<LoginLog, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        LoginLog filter = requestJson.getFilter();
        if (null == filter) {
            filter = new LoginLog();
        }
        DateTree dateTree = loginLogService.getDateTree(filter);

        responseJson.setChildren(dateTree);
        responseJson.setTotal(1L);
        responseJson.setSuccess(true);
        responseJson.setMsg("获取数据成功!");

        return responseJson;
    }

    @RequestMapping(value = "/get")
    public @ResponseBody Object get(@RequestBody RequestJson<LoginLog, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();
        LoginLog filter = requestJson.getFilter();
        if (null == filter) {
            filter = new LoginLog();
        }
        Page<LoginLog> page = requestJson.getPage();
        if (null == page) {
            List<LoginLog> loginLogList = loginLogService.findList(filter);
            responseJson.setData(loginLogList);
            responseJson.setTotal(Long.valueOf(loginLogList.size()));
        } else {
            Page<LoginLog> pageInfo = loginLogService.findPage(page, filter);
            responseJson.setData(pageInfo.getList());
            responseJson.setTotal(pageInfo.getCount());
        }
        responseJson.setSuccess(true);
        responseJson.setMsg("获取数据成功!");

        return responseJson;
    }

}
