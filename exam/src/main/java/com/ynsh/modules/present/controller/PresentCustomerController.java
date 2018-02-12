package com.ynsh.modules.present.controller;

import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.present.dao.PresentTypeDao;
import com.ynsh.modules.present.entity.CustomerInfo;
import com.ynsh.modules.present.entity.PresentCustomer;
import com.ynsh.modules.present.entity.PresentType;
import com.ynsh.modules.present.service.PresentCustomerService;
import com.ynsh.modules.sys.entity.User;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by syb on 18-1-22.
 */
@Controller
@RequestMapping("${presentPath}/presentCustomer")
public class PresentCustomerController extends CrudController<PresentCustomerService,PresentCustomer,Long> {

    private static final Logger logger = LoggerFactory.getLogger(com.ynsh.modules.present.controller.PresentCustomerController.class);

    @Autowired
    private PresentTypeDao presentTypeDao;

    @RequestMapping("/getcustomerpresents")
    public @ResponseBody Object getSingleCustomerPresents(@RequestBody RequestJson<PresentCustomer,Long> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        try {
            PresentCustomer presentCustomer =  requestJson.getEntities().get(0);
            presentCustomer.setPage(requestJson.getPage());
            UserOrganization uo = UserUtils.getUserOrganization();
            presentCustomer.setOrgCode(uo.getOrganizationCode());
            List<PresentCustomer> customerPresents = service.getCustomerPresentsByIdentityNumber(presentCustomer);

            responseJson.setMsg("获取客户的礼品成功");
            responseJson.setData(customerPresents);
            responseJson.setTotal(new Long(presentCustomer.getPage().getCount()));
            responseJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setTotal(0L);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/distributepresent")
    public @ResponseBody
    Object distributePresent(@RequestBody Map<String,String> requestBodyParams) {
        ResponseJson responseJson = new ResponseJson();

        try {
            Long id = new Long(requestBodyParams.get("id"));
            if (!service.CheckPresentValidation2(id)){
                responseJson.setSuccess(false);
                responseJson.setMsg("不在该礼品的领取期限内或不符合领取条件");
            }
            else {
                service.distributePresent(id);
                responseJson.setMsg("领取礼品成功");
                responseJson.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setTotal(0L);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/registerpresent")
    public @ResponseBody
    Object registerPresent(@RequestBody Map<String,String> requestBodyParams) {
        ResponseJson responseJson = new ResponseJson();
        String orgCode = null;

        try {
            String identityNumber = requestBodyParams.get("identityNumber");
            Long presentId = new Long(requestBodyParams.get("presentId"));
            String customerName = requestBodyParams.get("customerName");
            String phoneNumber = requestBodyParams.get("phoneNumber");
            String accountNo = requestBodyParams.get("accountNo");
            String remarks = requestBodyParams.get("remarks");

            //1-检验礼品是否是可任何人领取的、检验礼品是否过期
            if (!service.CheckPresentValidation(presentId)){
                responseJson.setSuccess(false);
                responseJson.setMsg("不在该礼品的领取期限内或不符合领取条件");
            }
            else {
                //2-检验礼品是否已经被领取过
                orgCode = service.CheckPresentRegister(identityNumber,presentId);
                if (orgCode != null){
                    responseJson.setSuccess(false);
                    responseJson.setMsg("该礼品已在"+orgCode+"领取");
                }
                else {
                    //登记客户礼品
                    service.RegisterPresent(customerName,identityNumber,presentId,remarks,phoneNumber,accountNo);
                    responseJson.setMsg("登记礼品成功");
                    responseJson.setSuccess(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setTotal(0L);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/getvalidpresents")
    public @ResponseBody Object getValidPresents(){
        ResponseJson responseJson = new ResponseJson();
        UserOrganization uo = UserUtils.getUserOrganization();

        if (uo.getUser().isAdmin()){
            try {
                List<PresentType> presentTypes = presentTypeDao.findList(new PresentType());
                List<PresentType> filteredPresentTypes = new ArrayList<>();

                //过滤掉产品中不在有效期限内的产品
                for (int i=0;i<presentTypes.size();i++){
                    Date currentDate = service.getOnlyDate(new Date());
                    Date startDate = service.getOnlyDate(presentTypes.get(i).getStartDate());
                    Date endDate = service.getOnlyDate(presentTypes.get(i).getEndDate());
                    if (currentDate.before(startDate)||currentDate.after(endDate)) continue;
                    else {
                        filteredPresentTypes.add(presentTypes.get(i));
                    }
                }
                responseJson.setSuccess(true);
                responseJson.setMsg("管理员获取产品成功");
                responseJson.setData(filteredPresentTypes);
                responseJson.setTotal(new Long(filteredPresentTypes.size()));
            }catch (Exception e){
                e.printStackTrace();
                responseJson.setSuccess(false);
                responseJson.setMsg(e.getMessage());
            }
        }

        else{
            try {
                PresentType presentType = new PresentType();
                presentType.setOrgCode(uo.getOrganizationCode());
                Date currentDate = service.getOnlyDate(new Date());
                //获取到该柜员所在网点的所有产品
                List<PresentType> presentTypeList = presentTypeDao.getValidPresents(presentType);
                List<PresentType> filteredPresentTypeList = new ArrayList<>();
                //过滤掉产品中不在有效期限内的产品
                for (int i=0;i<presentTypeList.size();i++){
                    Date startDate = service.getOnlyDate(presentTypeList.get(i).getStartDate());
                    Date endDate = service.getOnlyDate(presentTypeList.get(i).getEndDate());
                    if (currentDate.before(startDate)||currentDate.after(endDate)) continue;
                    else {
                        filteredPresentTypeList.add(presentTypeList.get(i));
                    }
                }
                responseJson.setMsg("获取可登记产品成功");
                responseJson.setSuccess(true);
                responseJson.setTotal(new Long(filteredPresentTypeList.size()));
                responseJson.setData(filteredPresentTypeList);
            }catch (Exception e){
                e.printStackTrace();
                responseJson.setSuccess(false);
                responseJson.setMsg(e.getMessage());
            }
        }

        return responseJson;
    }

    @RequestMapping("/getrecords")
    public @ResponseBody
    Object getRecords(@RequestBody RequestJson<PresentCustomer,Long> requestJson){
        ResponseJson responseJson = new ResponseJson();
        List<PresentCustomer> entities = requestJson.getEntities();
        PresentCustomer presentCustomer = new PresentCustomer();
        List<PresentCustomer> presentCustomerList = new ArrayList<>();
        try {
            if (entities != null && entities.size() > 0) presentCustomer = entities.get(0);
            presentCustomer.setOrganizationId(UserUtils.getUserOrganization().getOrganizationId());

            Page<PresentCustomer> page = requestJson.getPage();
            presentCustomer.setPage(page);
            page.setList(service.getRecords(presentCustomer));

            responseJson.setSuccess(true);
            responseJson.setTotal(page.getCount());
            responseJson.setData(page.getList());
            responseJson.setMsg("获取领取记录成功");
        }catch (Exception e){
            e.printStackTrace();
            responseJson.setSuccess(false);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/getcustomerinfo")
    public @ResponseBody Object getCustomerInfo(@RequestBody Map<String,String> requestBodyParams){
        ResponseJson responseJson = new ResponseJson();

        String identityNumber = requestBodyParams.get("identityNumber");
        CustomerInfo customerInfo = service.getCustomerNoByIdentityNumber(identityNumber);

        if (customerInfo != null){
            String accountNo = service.getAccountNoByCustomerNo(customerInfo.getCustomerNo());
            customerInfo.setAccountNo(accountNo);

            if (accountNo != null){
                responseJson.setSuccess(true);
                responseJson.setTotal(new Long(1));
                responseJson.setData(customerInfo);
                responseJson.setMsg("获取客户信息成功");
            }else {
                responseJson.setSuccess(true);
                responseJson.setMsg("查询不到该客户的账户信息，请为客户开立账户，并手工登记该账户到“帐号或卡”字段中");
            }
        }
        else {
            responseJson.setSuccess(true);
            responseJson.setMsg("查询不到该客户的账户信息，请为客户开立账户，并手工登记该账户到“帐号或卡”字段中");
        }


        return responseJson;
    }

}
