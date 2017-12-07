package com.ynsh.controller;

import com.ynsh.model.*;
import com.ynsh.service.UnitService;
import com.ynsh.service.UserService;
import com.ynsh.service.XiaoChengXuService;
import com.ynsh.service.impl.XiaoChengXuServiceImpl;
import com.ynsh.util.Constant;
import com.ynsh.util.ResponseJson;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by syb on 2017/8/31.
 */

@Controller
@RequestMapping("xiaochengxu")
public class XiaoChengXuController {

    private static Logger logger = LoggerFactory.getLogger(XiaoChengXuController.class);
    @Autowired
    private XiaoChengXuService xiaoChengXuService;

    @Autowired
    private UnitService unitService;

    @RequestMapping("/getgroupitems.do")
    public @ResponseBody Object getGroupItems() {
        ResponseJson responseJson = new ResponseJson();
        List<MainPageGroup> mainPageGroups = new ArrayList<>();

        try {
            mainPageGroups = xiaoChengXuService.getAllGroups();
            responseJson.setSuccess(true);
            responseJson.setData(mainPageGroups);
        }catch (Exception e){
            responseJson.setSuccess(false);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/getunitdetail.do")
    public @ResponseBody Object getUnitDetail(@RequestParam("openid") String openid, @RequestParam("unitid") Long unitid){
        ResponseJson responseJson = new ResponseJson();
        ZanedUnit zanedUnit = new ZanedUnit();
        FocusedUnit focusedUnit = new FocusedUnit();

        zanedUnit.setUnitId(unitid);
        zanedUnit.setOpenId(openid);
        focusedUnit.setUnitId(unitid);
        focusedUnit.setOpenId(openid);

        Unit resultUnit = unitService.selectById(unitid);
        ZanedUnit resultZanedUnit = null;
        FocusedUnit resultFocusedUnit = null;

        try {
            resultZanedUnit = xiaoChengXuService.getZanedClient(zanedUnit);
            resultFocusedUnit = xiaoChengXuService.getFocusedClient(focusedUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultZanedUnit != null) resultUnit.setZaned(true);
        else resultUnit.setZaned(false);
        if (resultFocusedUnit != null) resultUnit.setFocused(true);
        else resultUnit.setFocused(false);

        logger.info("获取到的数据是：{}",resultUnit);
        responseJson.setMsg("获取数据成功");
        responseJson.setSuccess(true);
        responseJson.setData(resultUnit);

        return responseJson;
    }

    @RequestMapping("/onLogin.do")
    public @ResponseBody Object getOpenId(@RequestParam("code") String code){
        ResponseJson responseJson = new ResponseJson();
        Client client = new Client();
        try {
            client.setOpenId(xiaoChengXuService.getOpenIdByCode(code));
        }catch (Exception e){
            logger.error("通过code获取openid时错误");

            responseJson.setSuccess(false);
            return responseJson;
        }

        responseJson.setSuccess(true);
        responseJson.setData(client);
        return responseJson;
    }

    @RequestMapping("/getlist.do")
    public @ResponseBody Object getList(@RequestParam("id") Long id,@RequestParam("pagenum") int pageNum){
        ResponseJson responseJson = new ResponseJson();

        MainPageGroupItem mainPageGroupItem= new MainPageGroupItem();
        mainPageGroupItem.setId(id);

        List<Unit> listUnits = unitService.getMainPageGroupItemUnitsList(mainPageGroupItem);

        List<Unit> list1,list2,list3;
        if (pageNum == 1){
            if (listUnits.size()>=6) list1 = listUnits.subList(0,6);
            else list1 = listUnits.subList(0,listUnits.size());

            responseJson.setMsg("获取数据成功");
            responseJson.setSuccess(true);
            responseJson.setTotal(new Long(list1.size()));
            responseJson.setData(list1);
        }
        else if (pageNum == 2){
            if (listUnits.size()>=12) list2 = listUnits.subList(6,12);
            else list2 = listUnits.subList(6,listUnits.size());

            responseJson.setMsg("获取数据成功");
            responseJson.setSuccess(true);
            responseJson.setTotal(new Long(list2.size()));
            responseJson.setData(list2);
        }
        else if (pageNum == 3){
            list3 = listUnits.subList(12,listUnits.size());

            responseJson.setMsg("获取数据成功");
            responseJson.setSuccess(true);
            responseJson.setTotal(new Long(list3.size()));
            responseJson.setData(list3);
        }
        else {
            responseJson.setData(null);
            responseJson.setTotal(new Long(0));
            responseJson.setSuccess(true);
        }




        return responseJson;
    }

}
