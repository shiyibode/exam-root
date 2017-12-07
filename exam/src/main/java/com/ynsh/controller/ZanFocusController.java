package com.ynsh.controller;

import com.ynsh.model.FocusedUnit;
import com.ynsh.model.ZanedUnit;
import com.ynsh.service.ZanFocusService;
import com.ynsh.util.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by syb on 2017/10/14.
 */
@Controller
@RequestMapping("zanfocus")
public class ZanFocusController {
    private static Logger logger = LoggerFactory.getLogger(XiaoChengXuController.class);

    @Autowired
    private ZanFocusService zanFocusService;

    @RequestMapping("/zan.do")
    public @ResponseBody Object zanUnit(@RequestParam("unitid") Long unitId, @RequestParam("openid") String openId){
        ResponseJson responseJson = new ResponseJson();

        ZanedUnit zanedUnit = new ZanedUnit();

        zanedUnit.setOpenId(openId);
        zanedUnit.setUnitId(unitId);

        ZanedUnit resultZanedUnit = zanFocusService.getZanedUnit(zanedUnit);

        if (resultZanedUnit == null){
            zanFocusService.zanUnit(zanedUnit);
            zanFocusService.addZanedTimes(zanedUnit);
        }
        else if (resultZanedUnit.getDelFlag()){
            zanFocusService.reZanUnit(zanedUnit);
            zanFocusService.addZanedTimes(zanedUnit);
        }
        else{
            zanFocusService.deleteZanUnit(zanedUnit);
            zanFocusService.reduceZanedTimes(zanedUnit);
        }

        responseJson.setMsg("成功");
        responseJson.setSuccess(true);
        return responseJson;
    }

    @RequestMapping("/focus.do")
    public @ResponseBody Object focusUnit(@RequestParam("unitid") Long unitId, @RequestParam("openid") String openId){
        ResponseJson responseJson = new ResponseJson();

        FocusedUnit focusedUnit = new FocusedUnit();

        focusedUnit.setOpenId(openId);
        focusedUnit.setUnitId(unitId);

        FocusedUnit resultFocusUnit = zanFocusService.getFocusedUnit(focusedUnit);

        if (resultFocusUnit == null) {
            zanFocusService.focusUnit(focusedUnit);
            zanFocusService.addFocusedTimes(focusedUnit);
        }
        else if (resultFocusUnit.getDelFlag()) {
            zanFocusService.reFocusUnit(focusedUnit);
            zanFocusService.addFocusedTimes(focusedUnit);
        }
        else {
            zanFocusService.deleteFocusUnit(focusedUnit);
            zanFocusService.reduceFocusedTimes(focusedUnit);
        }

        responseJson.setMsg("成功");
        responseJson.setSuccess(true);
        return responseJson;
    }

}
