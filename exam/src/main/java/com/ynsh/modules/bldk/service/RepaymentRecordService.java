package com.ynsh.modules.bldk.service;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.bldk.dao.RepaymentRecordDao;
import com.ynsh.modules.bldk.entity.RepaymentRecord;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ynsh.modules.sys.utils.DataScopeUtils.dataScopeFilter;

/**
 * Created by syb on 18-1-10.
 */
@Service
@Transactional(readOnly = true)
public class RepaymentRecordService extends CrudService<RepaymentRecordDao,RepaymentRecord,Long>{

    public Page<RepaymentRecord> findPage(Page<RepaymentRecord> page, RepaymentRecord entity) {
        UserOrganization userOrganization = UserUtils.getUserOrganization();
        entity.getSqlMap().put("dsf",dataScopeFilter(Global.getBldkModuleId(), userOrganization, "o", ""));

        entity.setPage(page);
        page.setList(dao.findList(entity));
        logger.info("");
        return page;
    }

    public List<RepaymentRecord> findListByNonPerformingLoanId(Long nonPerformingLoanId){
        return dao.findListByNonPerformingLoanId(nonPerformingLoanId);
    }

}
