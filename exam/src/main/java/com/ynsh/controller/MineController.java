package com.ynsh.controller;

import com.ynsh.model.Client;
import com.ynsh.model.Unit;
import com.ynsh.service.UserService;
import com.ynsh.service.ZanFocusService;
import com.ynsh.util.Page;
import com.ynsh.util.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by syb on 2017/10/23.
 */
@Controller
@RequestMapping("mine")
public class MineController {
    private static Logger logger = LoggerFactory.getLogger(MineController.class);

    @Autowired
    private ZanFocusService zanFocusService;
    @Autowired
    private UserService userService;

    @RequestMapping("/getfocusedunits.do")
    public @ResponseBody Object getFocusedUnits(@RequestParam("openid") String openId, @RequestParam("pagenum") int pageNum){
        ResponseJson responseJson = new ResponseJson();
        Client client = new Client();
        client.setOpenId(openId);
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(pageNum);
        page.setPageSize(6);
        page.setOffset(page.getPageSize()*(pageNum-1));
        client.setPage(page);

        List<Unit> units = zanFocusService.getSingleClientFocusedUnits(client);
        responseJson.setSuccess(true);
        responseJson.setTotal(new Long(units.size()));
        responseJson.setData(units);
        return responseJson;
    }

    @RequestMapping("/getzanedunits.do")
    public @ResponseBody Object getZanedUnits(@RequestParam("openid") String openId, @RequestParam("pagenum") int pageNum){
        ResponseJson responseJson = new ResponseJson();
        Client client = new Client();
        client.setOpenId(openId);
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(pageNum);
        page.setPageSize(6);
        page.setOffset(page.getPageSize()*(pageNum-1));
        client.setPage(page);

        List<Unit> units = zanFocusService.getSingleClientZanedUnits(client);
        responseJson.setSuccess(true);
        responseJson.setTotal(new Long(units.size()));
        responseJson.setData(units);
        return responseJson;
    }

    @RequestMapping("/getmaincontactnumber.do")
    public @ResponseBody Object getMainContactNumber(){
        ResponseJson responseJson = new ResponseJson();
        String phoneNumber = null;
        try {
            phoneNumber = userService.getMineContactNumber();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        responseJson.setData(phoneNumber);
        responseJson.setTotal(new Long(1));
        responseJson.setSuccess(true);
        return responseJson;
    }

}
