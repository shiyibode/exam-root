package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.BaseController;
import com.ynsh.modules.sys.entity.DateTree;
import com.ynsh.modules.sys.entity.OperationLog;
import com.ynsh.modules.sys.service.OperationLogService;
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
@RequestMapping("${sysPath}/operationlog")
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(value = "/dateTree")
    public @ResponseBody Object dataTree(@RequestBody RequestJson<OperationLog, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        OperationLog filter = requestJson.getFilter();
        if (null == filter) {
            filter = new OperationLog();
        }
        DateTree dateTree = operationLogService.getDateTree(filter);

        responseJson.setChildren(dateTree);
        responseJson.setTotal(1L);
        responseJson.setSuccess(true);
        responseJson.setMsg("获取数据成功!");

        return responseJson;
    }

    @RequestMapping(value = "/get")
    public @ResponseBody Object get(@RequestBody RequestJson<OperationLog, Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();
        OperationLog filter = requestJson.getFilter();
        if (null == filter) {
            filter = new OperationLog();
        }
        Page<OperationLog> page = requestJson.getPage();
        if (null == page) {
            List<OperationLog> operationLogList = operationLogService.findList(filter);
            responseJson.setData(operationLogList);
            responseJson.setTotal(Long.valueOf(operationLogList.size()));
        } else {
            Page<OperationLog> pageInfo = operationLogService.findPage(page, filter);
            responseJson.setData(pageInfo.getList());
            responseJson.setTotal(pageInfo.getCount());
        }
        responseJson.setSuccess(true);
        responseJson.setMsg("获取数据成功!");

        return responseJson;
    }

}
