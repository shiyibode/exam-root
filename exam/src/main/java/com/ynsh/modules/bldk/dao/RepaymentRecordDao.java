package com.ynsh.modules.bldk.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.bldk.entity.RepaymentRecord;

import java.util.List;

/**
 * Created by syb on 18-1-10.
 */
@MyBatisDao
public interface RepaymentRecordDao extends CrudDao<RepaymentRecord,Long>{

    List<RepaymentRecord> findListByNonPerformingLoanId(Long nonPerfromingLoanId);
}
