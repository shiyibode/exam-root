package com.ynsh.modules.present.service;

import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.present.dao.PresentCustomerDao;
import com.ynsh.modules.present.dao.PresentTypeDao;
import com.ynsh.modules.present.entity.CustomerInfo;
import com.ynsh.modules.present.entity.PresentCustomer;
import com.ynsh.modules.present.entity.PresentType;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by syb on 18-1-22.
 */
@Service
public class PresentCustomerService extends CrudService<PresentCustomerDao,PresentCustomer,Long> {

    @Autowired
    private PresentTypeDao presentTypeDao;

    public List<PresentCustomer> getCustomerPresentsByIdentityNumber(PresentCustomer presentCustomer){
        return dao.getCustomerPresentsByIdentityNumber(presentCustomer);
    }

    @Transactional(readOnly = false)
    public void distributePresent(Long id){
        Long organizationId = UserUtils.getUserOrganization().getOrganizationId();
        PresentCustomer presentCustomer = new PresentCustomer();
        presentCustomer.setOrganizationId(organizationId);
        presentCustomer.setId(id);
        presentCustomer.setUpdateBy(UserUtils.getUser());
        dao.distributePresent(presentCustomer);
    }

    /**
     * 校验是否所有人均可领取该礼品，校验礼品是否在有效期内
     * @param presentId 礼品id
     * @throws Exception
     */
    public Boolean CheckPresentValidation(Long presentId) throws Exception{
        PresentType temp = new PresentType();
        temp.setId(presentId);
        PresentType presentType = presentTypeDao.get(temp);
        if(!presentType.getDistributeType().equals(PresentType.DISTRIBUTE_TYPE_EVERYONE)) return false;

        Date startDate = getOnlyDate(presentType.getStartDate());
        Date endDate = getOnlyDate(presentType.getEndDate());
        Date currentDate = getOnlyDate(new Date());

        if (currentDate.before(startDate)||currentDate.after(endDate)) return false;

        return true;
    }

    /**
     * 校验是否指定人可领取该礼品，校验礼品是否在有效期内
     * @param id presentCustomer表的id
     * @throws Exception
     */
    public Boolean CheckPresentValidation2(Long id) throws Exception{
        PresentCustomer t = new PresentCustomer();
        t.setId(id);
        PresentCustomer presentCustomer = dao.get(t);
        PresentType temp = new PresentType();
        temp.setId(presentCustomer.getPresentId());
        PresentType presentType = presentTypeDao.get(temp);
        if(!presentType.getDistributeType().equals(PresentType.DISTRIBUTE_TYPE_SPECIFIC)) return false;

        Date startDate = getOnlyDate(presentType.getStartDate());
        Date endDate = getOnlyDate(presentType.getEndDate());
        Date currentDate = getOnlyDate(new Date());

        if (currentDate.before(startDate)||currentDate.after(endDate)) return false;

        return true;
    }

    /**检查该客户在全行是否已经领取过该礼品
     * @param identityNumber 身份证号码
     * @param presentId 礼品id
     * @return
     */
    public String CheckPresentRegister(String identityNumber, Long presentId){
        List<PresentCustomer> presentCustomers = new ArrayList<>();
        PresentCustomer pc = new PresentCustomer();
        pc.setIdentityNumber(identityNumber);
        pc.setPresentId(presentId);

        presentCustomers = dao.getCustomerPresentsByIdentityNumberAndPresentId(pc);
        if (presentCustomers.size()==0) return null;
        return presentCustomers.get(0).getOrgCode();
    }

    @Transactional(readOnly = false)
    public Boolean RegisterPresent(String customerName,String identityNumber,Long presentId,String remarks,String phoneNumber,String accountNo){
        PresentCustomer presentCustomer = new PresentCustomer();
        presentCustomer.setIdentityNumber(identityNumber);
        presentCustomer.setPresentId(presentId);
        presentCustomer.setCustomerName(customerName);
        presentCustomer.setDistributeFlag("1");
        UserOrganization uo = UserUtils.getUserOrganization();
        presentCustomer.setOrgCode(uo.getOrganizationCode());
        presentCustomer.setOrganizationId(uo.getOrganizationId());
        presentCustomer.setRemarks(remarks);
        presentCustomer.setPhoneNumber(phoneNumber);
        presentCustomer.setAccountNo(accountNo);

        save(presentCustomer);
        return true;
    }

    public List<PresentCustomer> getRecords(PresentCustomer presentCustomer){
        return dao.getRecords(presentCustomer);
    }


    public Date getOnlyDate(Date date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(date));
    }


    public CustomerInfo getCustomerNoByIdentityNumber(String identityNumber){
        return dao.getCustomerNoByIdentityNumber(identityNumber);
    }

    public String getAccountNoByCustomerNo(String customerNo){
        return  dao.getAccountNoByCustomerNo(customerNo);
    }
}
