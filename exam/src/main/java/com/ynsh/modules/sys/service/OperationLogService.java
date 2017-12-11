package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.sys.dao.OperationLogDao;
import com.ynsh.modules.sys.entity.DateTree;
import com.ynsh.modules.sys.entity.OperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenjianjun on 2016/10/24.
 */
@Service
@Transactional(readOnly = true)
public class OperationLogService extends CrudService<OperationLogDao, OperationLog, Long> {

    public DateTree getDateTree(OperationLog operationLog) {
        List<DateTree> dateTreeList = dao.selectDateList(operationLog);
        return DateTree.listToTree(dateTreeList);
    }


}
