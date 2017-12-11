package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.sys.dao.LoginLogDao;
import com.ynsh.modules.sys.entity.DateTree;
import com.ynsh.modules.sys.entity.LoginLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenjianjun on 2016/10/24.
 */
@Service
@Transactional(readOnly = true)
public class LoginLogService extends CrudService<LoginLogDao, LoginLog, Long> {

    public DateTree getDateTree(LoginLog loginLog) {
        List<DateTree> dateTreeList = dao.selectDateList(loginLog);
        return DateTree.listToTree(dateTreeList);
    }


}
