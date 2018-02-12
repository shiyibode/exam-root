package com.ynsh.modules.present.controller;

import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.present.entity.PresentType;
import com.ynsh.modules.present.service.PresentTypeService;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by syb on 18-1-22.
 */

@Controller
@RequestMapping("${presentPath}/presentType")
public class PresentTypeController extends CrudController<PresentTypeService,PresentType,Long>{

    @RequestMapping(value = "/create")
    public @ResponseBody
    Object create(@RequestBody RequestJson<PresentType, Long> requestJson) {
        List<PresentType> entities = requestJson.getEntities();
        ResponseJson responseJson = new ResponseJson();

        if (UserUtils.getUser().isAdmin()){
            service.save(entities);
            responseJson.setSuccess(true);
            responseJson.setMsg("添加数据成功!");
            responseJson.setTotal(0L);
        }else {
            responseJson.setSuccess(false);
            responseJson.setMsg("非管理员不允许添加礼品种类");
        }

        return responseJson;
    }


}
