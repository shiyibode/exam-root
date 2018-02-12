package com.ynsh.modules.present.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.present.entity.CustomerInfo;
import com.ynsh.modules.present.entity.PresentCustomer;

import java.util.List;

/**
 * Created by syb on 18-1-22.
 */
@MyBatisDao
public interface PresentCustomerDao extends CrudDao<PresentCustomer,Long>{

    public List<PresentCustomer> getCustomerPresentsByIdentityNumber(PresentCustomer presentCustomer);

    public void distributePresent(PresentCustomer presentCustomer);

    public List<PresentCustomer> getCustomerPresentsByIdentityNumberAndPresentId(PresentCustomer presentCustomer);

    public List<PresentCustomer> getRecords(PresentCustomer presentCustomer);

    public CustomerInfo getCustomerNoByIdentityNumber(String identityNumber);

    public String getAccountNoByCustomerNo(String customerNo);

}
