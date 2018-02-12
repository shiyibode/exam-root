package com.ynsh.modules.bldk.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.bldk.entity.NonPerformingLoan;

import java.util.List;

/**
 * Created by syb on 18-1-9.
 */
@MyBatisDao
public interface NonPerformingLoanDao extends CrudDao<NonPerformingLoan,Long> {


}
