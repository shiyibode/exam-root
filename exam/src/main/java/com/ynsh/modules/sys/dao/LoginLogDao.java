package com.ynsh.modules.sys.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.sys.entity.DateTree;
import com.ynsh.modules.sys.entity.LoginLog;

import java.util.List;

/**
 * 日志DAO接口
 */
@MyBatisDao
public interface LoginLogDao extends CrudDao<LoginLog, Long> {

    public List<DateTree> selectDateList(LoginLog loginLog);
}
