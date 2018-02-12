package com.ynsh.modules.bldk.controller;

import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.bldk.entity.DailyPrincipalInterest;
import com.ynsh.modules.bldk.entity.NonPerformingLoan;
import com.ynsh.modules.bldk.entity.RepaymentRecord;
import com.ynsh.modules.bldk.service.NonPerformingLoanService;
import com.ynsh.modules.bldk.service.RepaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by syb on 18-1-8.
 */
@Controller
@RequestMapping("${bldkPath}/nonperformingloan")
public class NonPerformingLoanController extends CrudController<NonPerformingLoanService, NonPerformingLoan, Long> {

    private static final Logger logger = LoggerFactory.getLogger(com.ynsh.modules.sys.web.controller.MenuController.class);

    @Autowired
    private RepaymentRecordService repaymentRecordService;

    /**
     * 获取所有菜单分组(按系统模块划分),用于前端UI对菜单按模块分组
     * @return
     */
    @RequestMapping("/compute")
    public @ResponseBody Object compute(@RequestBody Map<String,String> requestBodyParams) {

        ResponseJson responseJson = new ResponseJson();
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");

        try {
            Long nonPerformingLoanId = new Long(requestBodyParams.get("nonPerformingLoanId"));
            Date computeDate = sdf.parse(requestBodyParams.get("computeDate"));
            NonPerformingLoan currentLoan = service.get(nonPerformingLoanId);
            List<RepaymentRecord> repaymentRecords = repaymentRecordService.findListByNonPerformingLoanId(nonPerformingLoanId);
            DailyPrincipalInterest record = service.getDailyPrincipalInterest(computeDate,currentLoan,repaymentRecords);


            responseJson.setSuccess(true);
            responseJson.setData(record);
            responseJson.setMsg("计算"+sdf.format(computeDate)+"日的本金、利息、复利成功");

        } catch (Exception e) {

            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setTotal(0L);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping(value = "/delete")
    @Override
    public @ResponseBody Object delete(@RequestBody RequestJson<NonPerformingLoan, Long> requestJson) {
        List<NonPerformingLoan> entities = requestJson.getEntities();
        ResponseJson responseJson = new ResponseJson();

        try {
            service.delete(entities);
            List<RepaymentRecord> records = repaymentRecordService.findListByNonPerformingLoanId(entities.get(0).getId());
            repaymentRecordService.delete(records);
            responseJson.setSuccess(true);
            responseJson.setMsg("删除数据成功!");
            responseJson.setTotal(0L);
        }catch (Exception e){
            responseJson.setSuccess(false);
            responseJson.setMsg("删除数据失败："+e.getMessage());
        }

        return responseJson;
    }

}