package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.TreeService;
import com.ynsh.modules.sys.dao.DictDao;
import com.ynsh.modules.sys.entity.Dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 */
@Service
@Transactional(readOnly = true)
public class DictService extends TreeService<DictDao, Dict, Long> {

    /**
     * 查询字段类型列表
     * @return
     */
    public List<String> findTypeList(){
        return dao.findTypeList(new Dict());
    }

}
