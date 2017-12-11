package com.ynsh.modules.sys.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.TreeDao;
import com.ynsh.modules.sys.entity.Dict;

import java.util.List;

/**
 * 字典DAO接口
 */
@MyBatisDao
public interface DictDao extends TreeDao<Dict, Long> {

    public List<String> findTypeList(Dict dict);
}
